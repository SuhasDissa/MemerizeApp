package app.suhasdissa.memerize.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.suhasdissa.memerize.backend.Children
import app.suhasdissa.memerize.backend.MemeApi
import kotlinx.coroutines.launch

sealed interface UiState{
    data class Success(val children: List<Children>) : UiState
    data class Error(val error: String) : UiState
    object Loading : UiState
}

class ImageViewModel() : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var memeUiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        getMemePhotos()
    }

    private fun getMemePhotos() {
        viewModelScope.launch {
            memeUiState = try {
                UiState.Success(MemeApi.retrofitService.getPhotos().data.children)
                //UiState.Success(MemeApi.retrofitService.getPhotos())
            }catch (e: Exception){
                UiState.Error(e.toString())
            }
        }
    }
}
