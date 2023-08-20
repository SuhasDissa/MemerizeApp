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
import app.suhasdissa.memerize.backend.database.entity.LemmyCommunity
import app.suhasdissa.memerize.backend.model.Sort
import app.suhasdissa.memerize.backend.repositories.LemmyMemeRepository
import app.suhasdissa.memerize.backend.viewmodels.state.MemeUiState
import kotlinx.coroutines.launch

class LemmyViewModel(private val lemmyRepository: LemmyMemeRepository) :
    ViewModel() {
    var memeUiState: MemeUiState by mutableStateOf(MemeUiState.Loading)
        private set

    var currentCommunity: LemmyCommunity? = null
        private set
    var currentSortTime: Sort = Sort.Top.Today
        private set

    fun getMemePhotos(
        community: LemmyCommunity? = currentCommunity,
        sort: Sort = Sort.Top.Today
    ) {
        currentCommunity = community!!
        currentSortTime = sort
        viewModelScope.launch {
            memeUiState = MemeUiState.Loading

            memeUiState = when (val data = lemmyRepository.getOnlineData(community, sort)) {
                null -> {
                    MemeUiState.Error("")
                }

                else -> {
                    MemeUiState.Success(data)
                }
            }
        }
    }

    fun getLocalMemes(community: LemmyCommunity = currentCommunity!!) {
        viewModelScope.launch {
            memeUiState = MemeUiState.Loading

            memeUiState = MemeUiState.Success(lemmyRepository.getLocalData(community))
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemerizeApplication)
                val lemmyRepository = application.container.lemmyMemeRepository
                LemmyViewModel(lemmyRepository)
            }
        }
    }
}
