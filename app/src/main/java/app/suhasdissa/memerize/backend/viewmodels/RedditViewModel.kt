/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.suhasdissa.memerize.MemerizeApplication
import app.suhasdissa.memerize.backend.repositories.Meme
import app.suhasdissa.memerize.backend.repositories.RedditRepository
import kotlinx.coroutines.launch

sealed interface DataState {
    data class Success(val memes: ArrayList<Meme>) : DataState
    data class Error(val error: String) : DataState
    object Loading : DataState
}

class RedditViewModel(private val redditRepository: RedditRepository) : ViewModel() {
    var dataState: DataState by mutableStateOf(DataState.Loading)
        private set

    fun getMemePhotos(subreddit: String, time: String) {
        viewModelScope.launch {
            dataState = DataState.Loading

            dataState = when (val data = redditRepository.getOnlineData(subreddit, time)) {
                null -> {
                    DataState.Error("")
                }

                else -> {
                    DataState.Success(data)
                }
            }
        }
    }

    fun getLocalMemes(subreddit: String) {
        viewModelScope.launch {
            dataState = DataState.Loading

            dataState = DataState.Success(redditRepository.getLocalData(subreddit))
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemerizeApplication)
                val redditRepository = application.container.redditRepository
                RedditViewModel(redditRepository = redditRepository)
            }
        }
    }
}