/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.secondary

import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import app.suhasdissa.memerize.utils.downloadUtil
import app.suhasdissa.memerize.utils.shareUrl
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun VideoView(
    url: String,
    modifier: Modifier = Modifier
) {
    val decodedUrl = remember { URLDecoder.decode(url, StandardCharsets.UTF_8.toString()) }
    val view = LocalView.current
    val context = LocalContext.current
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        val mExoPlayer = remember(context) {
            ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.Builder().setUri(decodedUrl).build()
                setMediaItem(mediaItem)
                playWhenReady = true
                prepare()
            }
        }
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            DisposableEffect(
                AndroidView(factory = { context ->
                    PlayerView(context).apply {
                        player = mExoPlayer
                    }
                })
            ) {
                onDispose {
                    mExoPlayer.release()
                }
            }
        }
        Surface(
            modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            Row(
                modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    downloadUtil(context, decodedUrl ?: "")
                }, modifier) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download Photo",
                        modifier.size(48.dp)
                    )
                }
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    shareUrl(context, decodedUrl ?: "")
                }, modifier) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Photo",
                        modifier.size(48.dp)
                    )
                }
            }
        }
    }
}
