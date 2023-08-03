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
import app.suhasdissa.memerize.backend.database.entity.LemmyCommunity
import app.suhasdissa.memerize.backend.repositories.LemmyRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface LemmyAboutState {
    data class Success(val community: LemmyCommunity) : LemmyAboutState
    data class Error(val community: String, val instance: String) : LemmyAboutState
    data class Loading(val community: String = "", val instance: String = "") : LemmyAboutState
}

class LemmyCommunityViewModel(private val lemmyRepository: LemmyRepository) : ViewModel() {

    val communities = lemmyRepository.getCommunities().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = listOf()
    )

    var aboutState: LemmyAboutState by mutableStateOf(LemmyAboutState.Loading())

    fun removeCommunity(community: LemmyCommunity) {
        viewModelScope.launch {
            lemmyRepository.removeCommunity(community)
        }
    }

    fun getInfo(instance: String, community: String) {
        viewModelScope.launch {
            aboutState = LemmyAboutState.Loading(community, instance)
            val lemmyInfo = lemmyRepository.getCommunityInfo(community, instance)
            if (lemmyInfo == null) {
                aboutState = LemmyAboutState.Error(community, instance)
            } else {
                aboutState = LemmyAboutState.Success(lemmyInfo)
                lemmyRepository.insertCommunity(lemmyInfo)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemerizeApplication)
                val lemmyRepository = application.container.lemmyRepository
                LemmyCommunityViewModel(lemmyRepository)
            }
        }
    }
}
