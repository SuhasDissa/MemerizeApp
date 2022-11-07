package app.suhasdissa.memerize.backend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed interface UiState{
    data class Success(val children: List<Children>) : UiState
    data class Error(val error: String) : UiState
    object Loading : UiState
}

class ImageViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var memeUiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        getMemePhotos()
    }

    private fun getMemePhotos() {
        viewModelScope.launch {
            memeUiState = try {
                UiState.Success(RedditApi.retrofitService.getPhotos().data.children)
            }catch (e: Exception){
                UiState.Error(e.toString())
            }
        }
    }
}
