/*******************************************************************************
Created By Suhas Dissanayake on 8/3/23, 11:19 AM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import app.suhasdissa.memerize.MemerizeApplication

@UnstableApi
class PlayerViewModel(appContext: Context) : ViewModel() {
    val player: ExoPlayer = ExoPlayer.Builder(appContext).build()

    fun playPause() {
        if (player.isPlaying) player.pause() else player.play()
    }

    fun seekTo(to: Long) {
        player.seekTo(to)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MemerizeApplication) // ktlint-disable max-line-length
                PlayerViewModel(application)
            }
        }
    }
}
