package app.suhasdissa.memerize.backend.viewmodels

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.suhasdissa.memerize.BuildConfig
import app.suhasdissa.memerize.backend.database.entity.Meme
import app.suhasdissa.memerize.utils.SaveDirectoryKey
import app.suhasdissa.memerize.utils.preferences
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoViewModel : ViewModel() {

    var downloadState: DownloadState by mutableStateOf(DownloadState.NotStarted)

    private suspend fun getBitmapFromUrl(url: String, context: Context): Bitmap? {
        val imageLoader = ImageLoader.Builder(context).build()
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        val result = imageLoader.execute(request)

        if (result is SuccessResult) {
            return result.drawable.toBitmap()
        }
        return null
    }

    fun savePhotoToDisk(meme: Meme, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                downloadState = DownloadState.Loading
            }
            val bitmap = getBitmapFromUrl(meme.url, context)
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
            val outputFile =
                saveDir.createFile(
                    "image/jpg",
                    "${meme.title.take(64)}-${
                        UUID.randomUUID().toString().take(8)
                    }.jpg".replace("[\\\\/:*?\"<>|]".toRegex(), "")
                )
            if (outputFile == null) {
                withContext(Dispatchers.Main) {
                    downloadState = DownloadState.Error
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to create file", Toast.LENGTH_LONG).show()
                }
                return@launch
            }
            if (bitmap != null) {
                try {
                    val outputStream = context.contentResolver.openOutputStream(outputFile.uri)!!
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Download Finished", Toast.LENGTH_LONG).show()
                        downloadState = DownloadState.NotStarted
                    }
                } catch (e: Exception) {
                    Log.e("Photo save", e.toString())
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Download Failed", Toast.LENGTH_LONG).show()
                        downloadState = DownloadState.Error
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    downloadState = DownloadState.Error
                }
            }
        }
    }

    fun shareImage(url: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = getBitmapFromUrl(url, context)
            if (bitmap != null) {
                try {
                    val outputFile = File(
                        context.cacheDir,
                        "${UUID.randomUUID()}.jpg"
                    )
                    val outputStream = FileOutputStream(outputFile)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_STREAM,
                            FileProvider.getUriForFile(
                                context,
                                BuildConfig.APPLICATION_ID + ".provider",
                                outputFile
                            )
                        )
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        type = "image/jpg"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Send Photo to..")
                    context.startActivity(shareIntent)
                } catch (e: Exception) {
                    Log.e("Share Image", e.toString())
                }
            }
        }
    }
}

sealed interface DownloadState {
    object NotStarted : DownloadState
    object Success : DownloadState
    object Error : DownloadState
    object Loading : DownloadState
}
