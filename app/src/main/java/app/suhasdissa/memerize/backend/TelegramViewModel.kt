package app.suhasdissa.memerize.backend


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed interface TelegramUiState {
    data class Success(val messages: List<Messages>) : TelegramUiState
    data class Error(val error: String) : TelegramUiState
    object Loading : TelegramUiState
}

class TelegramViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var state: TelegramUiState by mutableStateOf(TelegramUiState.Loading)
        private set

    init {
        getMemePhotos("chap_lin_sl","20")
    }

    fun getMemePhotos(channel:String,limit:String) {
        viewModelScope.launch {
            state = TelegramUiState.Loading
            state = try {
                TelegramUiState.Success(
                    TelegramApi.retrofitService.getChannelData(channel,limit).messages
                )
            } catch (e: Exception) {
                TelegramUiState.Error(e.toString())
            }
        }
    }
}