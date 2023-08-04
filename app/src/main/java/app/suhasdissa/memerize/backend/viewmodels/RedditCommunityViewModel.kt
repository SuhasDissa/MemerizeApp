/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 2:18 PM
Copyright (c) 2023
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
import app.suhasdissa.memerize.backend.repositories.CommunityRepository
import app.suhasdissa.memerize.backend.viewmodels.state.AboutCommunityState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RedditCommunityViewModel(private val redditRepository: CommunityRepository<RedditCommunity>) :
    ViewModel() {

    val communities = redditRepository.getCommunities().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = listOf()
    )

    var aboutCommunityState: AboutCommunityState by mutableStateOf(
        AboutCommunityState.Loading(
            RedditCommunity("")
        )
    )

    fun removeSubreddit(subreddit: RedditCommunity) {
        viewModelScope.launch {
            redditRepository.removeCommunity(subreddit)
        }
    }

    fun getSubredditInfo(subreddit: String) {
        viewModelScope.launch {
            aboutCommunityState = AboutCommunityState.Loading(RedditCommunity(subreddit))
            val subredditInfo = redditRepository.getCommunityInfo(RedditCommunity(subreddit))
            if (subredditInfo == null) {
                aboutCommunityState = AboutCommunityState.Error(RedditCommunity(subreddit))
            } else {
                aboutCommunityState = AboutCommunityState.Success(subredditInfo)
                redditRepository.insertCommunity(subredditInfo)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemerizeApplication)
                val redditRepository = application.container.redditCommunityRepository
                RedditCommunityViewModel(redditRepository = redditRepository)
            }
        }
    }
}
