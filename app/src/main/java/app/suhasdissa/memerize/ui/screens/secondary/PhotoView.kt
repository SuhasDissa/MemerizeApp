package app.suhasdissa.memerize.ui.screens.secondary

import android.view.SoundEffectConstants
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.database.entity.Meme
import app.suhasdissa.memerize.backend.viewmodels.DownloadState
import app.suhasdissa.memerize.backend.viewmodels.PhotoViewModel
import app.suhasdissa.memerize.utils.openBrowser
import coil.compose.AsyncImage
import java.lang.Float.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoView(
    meme: Meme,
    photoViewModel: PhotoViewModel = viewModel()
) {
    val context = LocalContext.current
    val view = LocalView.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = meme.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary
                )
            }, actions = {
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    photoViewModel.savePhotoToDisk(meme, context)
                }) {
                    Icon(
                        imageVector = when (photoViewModel.downloadState) {
                            DownloadState.Error -> Icons.Default.Error
                            DownloadState.Loading -> Icons.Default.Downloading
                            DownloadState.NotStarted -> Icons.Default.Download
                            DownloadState.Success -> Icons.Default.DownloadDone
                        },
                        contentDescription = stringResource(R.string.download_photo)
                    )
                }

                var showDropdown by remember { mutableStateOf(false) }
                IconButton(onClick = { showDropdown = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(
                            R.string.show_more_options
                        )
                    )
                }

                DropdownMenu(expanded = showDropdown, onDismissRequest = {
                    showDropdown = false
                }) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.share_photo)) },
                        onClick = {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            photoViewModel.shareImage(meme.url, context)
                            showDropdown = false
                        }
                    )
                    meme.postLink?.let {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.open_post)) },
                            onClick = {
                                openBrowser(context, it)
                                showDropdown = false
                            }
                        )
                    }
                }
            })
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            var scale by remember { mutableFloatStateOf(1f) }
            var offsetX by remember { mutableFloatStateOf(1f) }
            var offsetY by remember { mutableFloatStateOf(1f) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            awaitFirstDown()
                            do {
                                val event = awaitPointerEvent()
                                if (event.changes.size >= 2) {
                                    scale *= event.calculateZoom()
                                    scale = max(scale, 1f)
                                    val offset = event.calculatePan()
                                    val w = size.width * (scale - 1f) / 2
                                    offsetX = (offsetX + offset.x).coerceIn(-w, w)
                                    val h = size.height * (scale - 1f) / 2
                                    offsetY = (offsetY + offset.y).coerceIn(-h, h)
                                    event.changes.forEach {
                                        it.consume()
                                    }
                                }
                            } while (event.changes.any { it.pressed })
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = meme.url,
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
        }
    }
}
