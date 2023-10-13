package app.suhasdissa.memerize.backend.viewmodels

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import app.suhasdissa.memerize.backend.database.entity.Meme
import app.suhasdissa.memerize.utils.RedditVideoDownloader
import java.util.UUID
import kotlinx.coroutines.launch

class PlayerViewModel() : ViewModel() {
    var downloadState: DownloadState by mutableStateOf(DownloadState.NotStarted)

    var muted by mutableStateOf(false)

    @RequiresApi(Build.VERSION_CODES.O)
    fun downloadVideo(context: Context, meme: Meme) {
        val fileName = "${meme.title.take(64)}-${UUID.randomUUID().toString().take(8)}"
        viewModelScope.launch {
            downloadState = DownloadState.Loading
            val downloader = RedditVideoDownloader()
            val result =
                downloader.downloadRedditVideo(context.applicationContext, meme.url, fileName)
            downloadState = if (result) {
                Toast.makeText(context, "Download Finished", Toast.LENGTH_LONG).show()
                DownloadState.NotStarted
            } else {
                Toast.makeText(context, "Download Failed", Toast.LENGTH_LONG).show()
                DownloadState.Error
            }
        }
    }
}

fun Player.playPause() {
    if (isPlaying) pause() else play()
}
