/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.secondary

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.viewmodels.DownloadState
import app.suhasdissa.memerize.backend.viewmodels.PlayerViewModel
import app.suhasdissa.memerize.utils.PlayerState
import app.suhasdissa.memerize.utils.isPlayingState
import app.suhasdissa.memerize.utils.positionAndDurationState
import app.suhasdissa.memerize.utils.shareUrl
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun VideoView(
    url: String,
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel = viewModel(factory = PlayerViewModel.Factory)

) {
    val decodedUrl = remember { URLDecoder.decode(url, StandardCharsets.UTF_8.toString()) }

    DisposableEffect(Unit) {
        with(playerViewModel.player) {
            val mediaItem = MediaItem.Builder().setUri(decodedUrl).build()
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
            onDispose {
                stop()
            }
        }
    }
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(factory = { context ->
                PlayerView(context).apply {
                    player = playerViewModel.player
                    useController = false
                }
            }, modifier = Modifier.fillMaxSize())
        }
        PlayerController(playerViewModel, decodedUrl)
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun PlayerController(
    playerViewModel: PlayerViewModel = viewModel(factory = PlayerViewModel.Factory),
    decodedUrl: String
) {
    val view = LocalView.current
    with(playerViewModel.player) {
        Column(Modifier.padding(16.dp)) {
            val positionAndDuration by positionAndDurationState()
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(DateUtils.formatElapsedTime(positionAndDuration.first / 1000))
                var tempSliderPosition by remember { mutableStateOf<Float?>(null) }
                Slider(
                    modifier = Modifier.weight(1f),
                    value = tempSliderPosition ?: positionAndDuration.first.toFloat(),
                    onValueChange = { tempSliderPosition = it },
                    valueRange = 0f.rangeTo(
                        positionAndDuration.second?.toFloat() ?: Float.MAX_VALUE
                    ),
                    onValueChangeFinished = {
                        tempSliderPosition?.let {
                            playerViewModel.seekTo(it.toLong())
                        }
                        tempSliderPosition = null
                    }
                )
                Text(
                    positionAndDuration.second?.let { DateUtils.formatElapsedTime(it / 1000) }
                        ?: ""
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val context = LocalContext.current
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    playerViewModel.downloadVideo(context, decodedUrl)
                }) {
                    Icon(
                        imageVector = when (playerViewModel.downloadState) {
                            DownloadState.Error -> Icons.Default.Error
                            DownloadState.Loading -> Icons.Default.Downloading
                            DownloadState.NotStarted -> Icons.Default.Download
                            DownloadState.Success -> Icons.Default.DownloadDone
                        },
                        contentDescription = "Download Video"
                    )
                }
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    shareUrl(context, decodedUrl)
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Photo"
                    )
                }
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                    shape = CircleShape
                ) {
                    val playState by isPlayingState()
                    IconButton(
                        onClick = {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            playerViewModel.playPause()
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        when (playState) {
                            PlayerState.Buffer -> {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }

                            PlayerState.Play -> {
                                Icon(
                                    Icons.Default.Pause,
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = stringResource(R.string.pause)
                                )
                            }

                            PlayerState.Pause -> {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = stringResource(R.string.play)
                                )
                            }
                        }
                    }
                }
                var noMuted by remember(volume) { mutableStateOf(volume > 0f) }
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    volume = if (volume > 0f) 0f else 1f
                    noMuted = !noMuted
                }) {
                    if (noMuted) {
                        Icon(Icons.Default.VolumeUp, contentDescription = null)
                    } else {
                        Icon(Icons.Default.VolumeOff, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    PlayerController(decodedUrl = "")
}
