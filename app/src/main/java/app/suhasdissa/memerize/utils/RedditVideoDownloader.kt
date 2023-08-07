/*******************************************************************************
Created By Suhas Dissanayake on 8/7/23, 6:30 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.utils

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaMuxer
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import app.suhasdissa.memerize.backend.apis.FileDownloadApi
import app.suhasdissa.memerize.backend.apis.RedditVideoApi
import java.nio.ByteBuffer
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class RedditVideoDownloader {

    private val xmlRetrofit = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/")
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
        .build()

    private val downloadRetrofit = Retrofit.Builder()
        .baseUrl("https://v.redd.it/")
        .build()

    private val apiService: RedditVideoApi = xmlRetrofit.create(RedditVideoApi::class.java)

    private val fileDownloadApiService: FileDownloadApi =
        downloadRetrofit.create(FileDownloadApi::class.java)

    @SuppressLint("Recycle")
    suspend fun downloadRedditVideo(context: Context, url: String): Boolean {
        val urlS = getRedditUrls(url) ?: return false
        val redditUrl = Regex("https?://v\\.redd\\.it/\\S+/").find(url)?.value ?: return false

        val videoUrl = redditUrl + urlS.first
        val audioUrl = redditUrl + urlS.second
        return withContext(Dispatchers.IO) {
            val videoFile = async { downloadFile(videoUrl, context) }
            val audioFile = async { downloadFile(audioUrl, context) }
            val result = awaitAll(videoFile, audioFile)
            result[0]?.let {
                val outputFile = getOutputFile(context)
                val pfd = context.contentResolver.openFileDescriptor(outputFile.uri, "w")
                muxVideoAndAudio(it.uri.path!!, result[1]?.uri?.path, pfd!!)
            } ?: false
        }
    }

    /**
     * https://github.com/Docile-Alligator/Infinity-For-Reddit/blob/d0a9d9af9a46477a9bc1ff36af11278fcba06aa5/app/src/main/java/ml/docilealligator/infinityforreddit/services/DownloadRedditVideoService.java#L348C7-L426C10
     */
    @SuppressLint("WrongConstant")
    private fun muxVideoAndAudio(
        videoFilePath: String,
        audioFilePath: String?,
        pfd: ParcelFileDescriptor
    ): Boolean {
        try {
            val videoExtractor = MediaExtractor()
            videoExtractor.setDataSource(videoFilePath)
            val muxer = MediaMuxer(pfd.fileDescriptor, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
            videoExtractor.selectTrack(0)
            val videoFormat = videoExtractor.getTrackFormat(0)
            val videoTrack = muxer.addTrack(videoFormat)
            var sawEOS = false
            val offset = 100
            val sampleSize = 4096 * 1024
            val videoBuf = ByteBuffer.allocate(sampleSize)
            val audioBuf = ByteBuffer.allocate(sampleSize)
            val videoBufferInfo = MediaCodec.BufferInfo()
            videoExtractor.seekTo(0, MediaExtractor.SEEK_TO_CLOSEST_SYNC)

            // audio not present for all videos
            val audioExtractor = MediaExtractor()
            val audioBufferInfo = MediaCodec.BufferInfo()
            var audioTrack = -1
            if (audioFilePath != null) {
                audioExtractor.setDataSource(audioFilePath)
                audioExtractor.selectTrack(0)
                val audioFormat = audioExtractor.getTrackFormat(0)
                audioExtractor.seekTo(0, MediaExtractor.SEEK_TO_CLOSEST_SYNC)
                audioTrack = muxer.addTrack(audioFormat)
            }
            muxer.start()
            while (!sawEOS) {
                videoBufferInfo.offset = offset
                videoBufferInfo.size = videoExtractor.readSampleData(videoBuf, offset)
                if (videoBufferInfo.size < 0) {
                    sawEOS = true
                    videoBufferInfo.size = 0
                } else {
                    videoBufferInfo.presentationTimeUs = videoExtractor.sampleTime
                    videoBufferInfo.flags = videoExtractor.sampleFlags
                    muxer.writeSampleData(videoTrack, videoBuf, videoBufferInfo)
                    videoExtractor.advance()
                }
            }
            if (audioFilePath != null) {
                var sawEOS2 = false
                while (!sawEOS2) {
                    audioBufferInfo.offset = offset
                    audioBufferInfo.size = audioExtractor.readSampleData(audioBuf, offset)
                    if (audioBufferInfo.size < 0) {
                        sawEOS2 = true
                        audioBufferInfo.size = 0
                    } else {
                        audioBufferInfo.presentationTimeUs = audioExtractor.sampleTime
                        audioBufferInfo.flags = audioExtractor.sampleFlags
                        muxer.writeSampleData(audioTrack, audioBuf, audioBufferInfo)
                        audioExtractor.advance()
                    }
                }
            }
            muxer.stop()
            muxer.release()
        } catch (e: Exception) {
            Log.e("Video Muxer", e.toString())
            return false
        }
        pfd.close()
        return true
    }

    private suspend fun downloadFile(url: String, context: Context): DocumentFile? {
        return withContext(Dispatchers.IO) {
            try {
                val call = fileDownloadApiService.downloadFile(url)
                val response = call.execute()
                if (response.isSuccessful) {
                    val bytes = response.body()?.bytes()
                    val outputFile = getTempFile(context)
                    val outputStream = context.contentResolver.openOutputStream(outputFile.uri)!!
                    outputStream.write(bytes)
                    outputStream.flush()
                    outputStream.close()
                    outputFile
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e("File Download", e.toString())
                null
            }
        }
    }

    private suspend fun getRedditUrls(url: String): Pair<String, String>? {
        return try {
            val sets = apiService.getRedditData(url).period?.adaptationSet
            val videoUrl = sets?.getOrNull(0)?.representationList?.last()?.baseURL
            val audioUrl = sets?.getOrNull(1)?.representationList?.last()?.baseURL
            if (audioUrl == null || videoUrl == null) return null
            (videoUrl to audioUrl)
        } catch (e: Exception) {
            Log.e("Reddit Urls", e.toString())
            null
        }
    }

    private suspend fun getOutputFile(context: Context): DocumentFile {
        return withContext(Dispatchers.IO) {
            val prefDir =
                context.preferences.getString(SaveDirectoryKey, null)

            val saveDir = when {
                prefDir.isNullOrBlank() -> {
                    val dir =
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                        )
                    DocumentFile.fromFile(dir)
                }

                else -> DocumentFile.fromTreeUri(context, Uri.parse(prefDir))!!
            }

            saveDir.createFile("video/mp4", "${UUID.randomUUID()}.mp4")!!
        }
    }

    private suspend fun getTempFile(context: Context): DocumentFile {
        return withContext(Dispatchers.IO) {
            val saveDir = DocumentFile.fromFile(context.cacheDir)

            saveDir.createFile("video/mp4", "${UUID.randomUUID()}.mp4")!!
        }
    }
}
