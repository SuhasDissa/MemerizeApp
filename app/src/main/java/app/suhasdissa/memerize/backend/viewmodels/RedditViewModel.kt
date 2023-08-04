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
import app.suhasdissa.memerize.backend.database.entity.RedditCommunity
import app.suhasdissa.memerize.backend.database.entity.RedditMeme
import app.suhasdissa.memerize.backend.model.SortTime
import app.suhasdissa.memerize.backend.model.reddit
import app.suhasdissa.memerize.backend.repositories.MemeRepository
import app.suhasdissa.memerize.backend.viewmodels.state.MemeUiState
import kotlinx.coroutines.launch

class RedditViewModel(private val redditRepository: MemeRepository<RedditMeme, RedditCommunity>) :
    ViewModel() {
    var memeUiState: MemeUiState by mutableStateOf(MemeUiState.Loading)
        private set

    private var currentSubreddit: String? = null

    fun getMemePhotos(subreddit: String? = currentSubreddit, time: SortTime = SortTime.TODAY) {
        currentSubreddit = subreddit!!
        viewModelScope.launch {
            memeUiState = MemeUiState.Loading

            memeUiState = when (
                val data =
                    redditRepository.getOnlineData(RedditCommunity(subreddit), time.reddit)
            ) {
                null -> {
                    MemeUiState.Error("")
                }

                else -> {
                    MemeUiState.Success(data)
                }
            }
        }
    }

    fun getLocalMemes(subreddit: String = currentSubreddit!!) {
        viewModelScope.launch {
            memeUiState = MemeUiState.Loading

            memeUiState =
                MemeUiState.Success(redditRepository.getLocalData(RedditCommunity(subreddit)))
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemerizeApplication)
                val redditRepository = application.container.redditMemeRepository
                RedditViewModel(redditRepository = redditRepository)
            }
        }
    }
}
