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
import androidx.lifecycle.viewModelScope
import app.suhasdissa.memerize.backend.repositories.DefaultRedditRepository
import app.suhasdissa.memerize.backend.repositories.Meme
import kotlinx.coroutines.launch

sealed interface UiState {
    data class Success(val memes: List<Meme>) : UiState
    data class Error(val error: String) : UiState
    object Loading : UiState
}

class RedditViewModel : ViewModel() {
    var memeUiState: UiState by mutableStateOf(UiState.Loading)
        private set

    fun getMemePhotos(subreddit: String, time: String) {
        viewModelScope.launch {
            memeUiState = UiState.Loading
            memeUiState = try {
                UiState.Success(
                    DefaultRedditRepository().getData(subreddit, time)
                )
            } catch (e: Exception) {
                UiState.Error(e.toString())
            }
        }
    }
}