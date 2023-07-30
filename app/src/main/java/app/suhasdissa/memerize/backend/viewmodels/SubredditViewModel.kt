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
import app.suhasdissa.memerize.backend.database.entity.Subreddit
import app.suhasdissa.memerize.backend.repositories.RedditRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AboutState {
    data class Success(val subreddit: Subreddit) : AboutState
    data class Error(val subreddit: String) : AboutState
    data class Loading(val subreddit: String) : AboutState
}

class SubredditViewModel(private val redditRepository: RedditRepository) : ViewModel() {

    val subreddits = redditRepository.getSubreddits().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = listOf()
    )

    var subredditAboutState: AboutState by mutableStateOf(AboutState.Loading(""))

    fun getSubredditInfo(subreddit: String) {
        viewModelScope.launch {
            subredditAboutState = AboutState.Loading(subreddit)
            val subredditInfo = redditRepository.getSubredditInfo(subreddit)?.data
            if (subredditInfo == null) {
                subredditAboutState = AboutState.Error(subreddit)
            }
            val sub = Subreddit(
                subreddit,
                subredditInfo?.communityIconUrl,
                subredditInfo?.displayName ?: subreddit
            )
            subredditAboutState = AboutState.Success(sub)
            redditRepository.insertSubreddit(sub)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemerizeApplication)
                val redditRepository = application.container.redditRepository
                SubredditViewModel(redditRepository = redditRepository)
            }
        }
    }
}
