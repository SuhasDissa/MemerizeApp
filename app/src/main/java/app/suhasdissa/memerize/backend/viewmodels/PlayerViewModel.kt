/*******************************************************************************
Created By Suhas Dissanayake on 8/3/23, 11:19 AM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.viewmodels

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import app.suhasdissa.memerize.MemerizeApplication
import app.suhasdissa.memerize.utils.RedditVideoDownloader
import kotlinx.coroutines.launch

@UnstableApi
class PlayerViewModel(appContext: Context) : ViewModel() {
    val player: ExoPlayer = ExoPlayer.Builder(appContext).build()

    var downloadState: DownloadState by mutableStateOf(DownloadState.NotStarted)

    fun playPause() {
        if (player.isPlaying) player.pause() else player.play()
    }

    fun seekTo(to: Long) {
        player.seekTo(to)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun downloadVideo(context: Context, url: String) {
        viewModelScope.launch {
            downloadState = DownloadState.Loading
            val downloader = RedditVideoDownloader()
            val result = downloader.downloadRedditVideo(context.applicationContext, url)
            downloadState = if (result) {
                Toast.makeText(context, "Download Finished", Toast.LENGTH_LONG).show()
                DownloadState.NotStarted
            } else {
                Toast.makeText(context, "Download Failed", Toast.LENGTH_LONG).show()
                DownloadState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MemerizeApplication) // ktlint-disable max-line-length
                PlayerViewModel(application)
            }
        }
    }
}
