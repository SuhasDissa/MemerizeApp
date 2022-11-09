package app.suhasdissa.memerize.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
private fun VideoView(url: String, modifier: Modifier = Modifier) {
    val decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val mContext = LocalContext.current
        val mExoPlayer = remember(mContext) {
            ExoPlayer.Builder(mContext).build().apply {
                val mediaItem = MediaItem.Builder()
                    .setUri(Uri.parse(decodedUrl))
                    .build()
                setMediaItem(mediaItem)
                playWhenReady = true
                prepare()
            }
        }
        AndroidView(factory = { context ->
            StyledPlayerView(context).apply {
                player = mExoPlayer
            }
        })
    }
}