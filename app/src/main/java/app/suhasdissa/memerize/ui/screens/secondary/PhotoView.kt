/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.secondary

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.utils.downloadUtil
import app.suhasdissa.memerize.utils.shareUrl
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.lang.Float.max
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun PhotoView(photo: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val photoUrl = remember { URLDecoder.decode(photo, StandardCharsets.UTF_8.toString()) }
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        var scale by remember { mutableFloatStateOf(1f) }
        var offsetX by remember { mutableFloatStateOf(1f) }
        var offsetY by remember { mutableFloatStateOf(1f) }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            scale *= event.calculateZoom()
                            scale = max(scale, 1f)
                            val offset = event.calculatePan()
                            offsetX += offset.x
                            offsetY += offset.y
                        } while (event.changes.any { it.pressed })
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(photoUrl)
                    .crossfade(true).build(),
                contentDescription = stringResource(R.string.meme_photo),
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offsetX
                        translationY = offsetY
                    },
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img)
            )
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
                IconButton(onClick = { downloadUtil(context, photoUrl) }, modifier) {
                    Icon(
                        painter = painterResource(R.drawable.ic_download),
                        contentDescription = "Download Photo",
                        modifier.size(48.dp)
                    )
                }
                IconButton(onClick = {
                    shareUrl(context, photoUrl)
                }, modifier) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = "Share Photo",
                        modifier.size(48.dp)
                    )
                }
            }
        }
    }
}
