package app.suhasdissa.memerize.backend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed interface MemerizeUiState{
    data class Success(val children: List<MemerizeModel>) : MemerizeUiState
    data class Error(val error: String) : MemerizeUiState
    object Loading : MemerizeUiState
}

class MemerizeViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var memerizeUiState: MemerizeUiState by mutableStateOf(MemerizeUiState.Loading)
        private set

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            memerizeUiState = MemerizeUiState.Loading
            memerizeUiState = try {
                MemerizeUiState.Success(
                    MemerizeApi.retrofitService.getData()
                )
            } catch (e: Exception) {
                MemerizeUiState.Error(e.toString())
            }
        }
    }
}