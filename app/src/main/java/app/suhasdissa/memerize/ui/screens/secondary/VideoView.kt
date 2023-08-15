/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.secondary

import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.database.entity.Meme
import app.suhasdissa.memerize.backend.viewmodels.DownloadState
import app.suhasdissa.memerize.backend.viewmodels.PlayerViewModel
import app.suhasdissa.memerize.backend.viewmodels.playPause
import app.suhasdissa.memerize.utils.PlayerState
import app.suhasdissa.memerize.utils.isPlayingState
import app.suhasdissa.memerize.utils.positionAndDurationState
import app.suhasdissa.memerize.utils.shareUrl

@Composable
fun VideoView(
    meme: Meme,
    playWhenReady: Boolean = false,
    playerViewModel: PlayerViewModel = viewModel()

) {
    val context = LocalContext.current
    val player = remember(context) { ExoPlayer.Builder(context).build() }
    player.playWhenReady = playWhenReady
    DisposableEffect(Unit) {
        with(player) {
            val mediaItem = MediaItem.Builder().setUri(meme.url).build()
            setMediaItem(mediaItem)
            prepare()
            onDispose {
                player.release()
            }
        }
    }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(factory = { context ->
                PlayerView(context).apply {
                    this.player = player
                    useController = false
                }
            }, modifier = Modifier.fillMaxSize())
        }
        PlayerController(player, playerViewModel, meme.url)
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun PlayerController(
    player: Player,
    playerViewModel: PlayerViewModel = viewModel(),
    decodedUrl: String
) {
    val view = LocalView.current
    with(player) {
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
                            player.seekTo(it.toLong())
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
                            contentDescription = stringResource(R.string.download_video)
                        )
                    }
                }
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    shareUrl(context, decodedUrl)
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = stringResource(R.string.share_video)
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
                            player.playPause()
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
                        Icon(
                            Icons.Default.VolumeUp,
                            contentDescription = stringResource(R.string.mute_sound)
                        )
                    } else {
                        Icon(
                            Icons.Default.VolumeOff,
                            contentDescription = stringResource(R.string.un_mute_sound)
                        )
                    }
                }
                var repeat by remember(repeatMode) {
                    mutableStateOf(repeatMode == Player.REPEAT_MODE_ONE)
                }
                IconButton(onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    repeatMode =
                        if (repeatMode == Player.REPEAT_MODE_OFF) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
                    repeat = (repeatMode == Player.REPEAT_MODE_ONE)
                }) {
                    if (repeat) {
                        Icon(
                            Icons.Default.RepeatOn,
                            contentDescription = stringResource(R.string.turn_off_repeat)
                        )
                    } else {
                        Icon(
                            Icons.Default.Repeat,
                            contentDescription = stringResource(R.string.turn_on_repeat)
                        )
                    }
                }
            }
        }
    }
}
