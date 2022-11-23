/*******************************************************************************
 Created By Suhas Dissanayake on 11/23/22, 4:16 PM
 Copyright (c) 2022
 https://github.com/SuhasDissa/
 All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.secondary

import android.net.Uri
import androidx.compose.foundation.layout.*
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
import app.suhasdissa.memerize.utils.downloadUtil
import app.suhasdissa.memerize.utils.shareUrl
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


        val mExoPlayer = remember(context) {
            ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.Builder().setUri(Uri.parse(decodedUrl)).build()
                setMediaItem(mediaItem)
                playWhenReady = true
                prepare()
            }
        }
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
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
        Row(
            modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(onClick = { downloadUtil(context, decodedUrl) }, modifier) {
                Icon(
                    painter = painterResource(R.drawable.ic_download),
                    contentDescription = "Download Photo",
                    modifier.size(40.dp)
                )
            }
            IconButton(onClick = {
                shareUrl(context, decodedUrl)
            }, modifier) {
                Icon(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = "Share Photo",
                    modifier.size(40.dp)
                )
            }
        }
    }
}