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
import app.suhasdissa.memerize.backend.model.Sort
import app.suhasdissa.memerize.backend.repositories.RedditMemeRepository
import app.suhasdissa.memerize.backend.viewmodels.state.MemeUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class RedditViewModel(private val redditRepository: RedditMemeRepository) :
    ViewModel() {
    var memeUiState: MemeUiState by mutableStateOf(MemeUiState.Loading)
        private set

    var currentSubreddit: RedditCommunity? = null
        private set

    var currentSortTime: Sort = Sort.Top.Today
        private set

    fun getMemePhotos(
        subreddit: RedditCommunity? = currentSubreddit,
        sort: Sort = Sort.Top.Today
    ) {
        currentSubreddit = subreddit!!
        currentSortTime = sort
        viewModelScope.launch {
            memeUiState = MemeUiState.Loading

            memeUiState = when (
                val data =
                    redditRepository.getOnlineData(subreddit, sort)
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

    fun getLocalMemes(subreddit: RedditCommunity = currentSubreddit!!) {
        viewModelScope.launch {
            memeUiState = MemeUiState.Loading

            memeUiState =
                MemeUiState.Success(redditRepository.getLocalData(subreddit))
        }
    }

    fun getMultiMemes(communities: List<RedditCommunity>) {
        viewModelScope.launch {
            currentSubreddit = null
            memeUiState = MemeUiState.Loading
            val results = communities.map {
                async { redditRepository.getOnlineData(it, Sort.Top.Today) }
            }.awaitAll()
            val memeList: List<RedditMeme> = results.filterNotNull().flatten().shuffled()
            memeUiState = if (memeList.isEmpty()) {
                MemeUiState.Error("")
            } else {
                MemeUiState.Success(memeList)
            }
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
