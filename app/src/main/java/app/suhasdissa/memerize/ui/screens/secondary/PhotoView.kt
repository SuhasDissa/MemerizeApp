/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.secondary

import android.view.SoundEffectConstants
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.viewmodels.DownloadState
import app.suhasdissa.memerize.backend.viewmodels.PhotoViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.lang.Float.max
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun PhotoView(
    photo: String,
    photoViewModel: PhotoViewModel = viewModel()
) {
    val context = LocalContext.current
    val photoUrl = remember { URLDecoder.decode(photo, StandardCharsets.UTF_8.toString()) }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        var scale by remember { mutableFloatStateOf(1f) }
        var offsetX by remember { mutableFloatStateOf(1f) }
        var offsetY by remember { mutableFloatStateOf(1f) }

        val view = LocalView.current
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

                            val w = size.width * (scale - 1f) / 2
                            offsetX = (offsetX + offset.x).coerceIn(-w, w)
                            val h = size.height * (scale - 1f) / 2
                            offsetY = (offsetY + offset.y).coerceIn(-h, h)
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
                modifier = Modifier
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
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            Row(
                Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    photoViewModel.savePhotoToDisk(photoUrl, context)
                }) {
                    Icon(
                        imageVector = when (photoViewModel.downloadState) {
                            DownloadState.Error -> Icons.Default.Error
                            DownloadState.Loading -> Icons.Default.Downloading
                            DownloadState.NotStarted -> Icons.Default.Download
                            DownloadState.Success -> Icons.Default.DownloadDone
                        },
                        contentDescription = "Download Photo",
                        Modifier.size(48.dp)
                    )
                }
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    photoViewModel.shareImage(photoUrl, context)
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Photo",
                        Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}
