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
import app.suhasdissa.memerize.backend.repositories.TelegramRepository
import kotlinx.coroutines.launch

sealed interface TelegramUiState {
    data class Success(val memes: List<Meme>) : TelegramUiState
    data class Error(val error: String) : TelegramUiState
    object Loading : TelegramUiState
}

class TelegramViewModel(private val telegramRepository: TelegramRepository) : ViewModel() {
    var state: TelegramUiState by mutableStateOf(TelegramUiState.Loading)
        private set

    fun getMemePhotos(channel: String) {
        viewModelScope.launch {
            state = TelegramUiState.Loading
            state = TelegramUiState.Success(
                telegramRepository.getData(channel)
            )

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemerizeApplication)
                val telegramRepository = application.container.telegramRepository
                TelegramViewModel(telegramRepository = telegramRepository)
            }
        }
    }
}