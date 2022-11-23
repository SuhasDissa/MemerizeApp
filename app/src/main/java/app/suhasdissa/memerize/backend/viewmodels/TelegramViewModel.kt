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
import app.suhasdissa.memerize.backend.TelegramApi
import app.suhasdissa.memerize.backend.serializables.Messages
import kotlinx.coroutines.launch

sealed interface TelegramUiState {
    data class Success(val messages: List<Messages>) : TelegramUiState
    data class Error(val error: String) : TelegramUiState
    object Loading : TelegramUiState
}

class TelegramViewModel : ViewModel() {
    var state: TelegramUiState by mutableStateOf(TelegramUiState.Loading)
        private set

    fun getMemePhotos(channel: String) {
        viewModelScope.launch {
            state = TelegramUiState.Loading
            state = try {
                TelegramUiState.Success(
                    TelegramApi.retrofitService.getChannelData(channel).messages
                )
            } catch (e: Exception) {
                TelegramUiState.Error(e.toString())
            }
        }
    }
}