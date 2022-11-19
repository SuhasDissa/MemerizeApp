package app.suhasdissa.memerize.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.utils.DownloadUtil
import app.suhasdissa.memerize.utils.ShareUrl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun VideoView(url: String, modifier: Modifier = Modifier) {
    val decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
    val context = LocalContext.current
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        Card(
            modifier
                .padding(10.dp)
                .fillMaxWidth(.98f)
        ) {
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

                IconButton(onClick = { DownloadUtil(context, decodedUrl) }, modifier) {
                    Icon(
                        painter = painterResource(R.drawable.ic_download),
                        contentDescription = "Download Photo"
                    )
                }
                IconButton(onClick = {
                    ShareUrl(context, decodedUrl)
                }, modifier) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = "Share Photo"
                    )
                }
            }
        }

        val mContext = LocalContext.current
        val mExoPlayer = remember(mContext) {
            ExoPlayer.Builder(mContext).build().apply {
                val mediaItem = MediaItem.Builder().setUri(Uri.parse(decodedUrl)).build()
                setMediaItem(mediaItem)
                playWhenReady = true
                prepare()
            }
        }
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            DisposableEffect(
                AndroidView(factory = { context ->
                    StyledPlayerView(context).apply {
                        player = mExoPlayer
                    }
                })
            ) {
                onDispose {
                    mExoPlayer.release()
                }
            }
        }
    }
}