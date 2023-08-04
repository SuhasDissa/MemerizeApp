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
import app.suhasdissa.memerize.backend.repositories.CommunityRepository
import app.suhasdissa.memerize.backend.viewmodels.state.AboutCommunityState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LemmyCommunityViewModel(private val lemmyRepository: CommunityRepository<LemmyCommunity>) :
    ViewModel() {

    val communities = lemmyRepository.getCommunities().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = listOf()
    )

    var aboutCommutnityState: AboutCommunityState by mutableStateOf(
        AboutCommunityState.Loading(
            LemmyCommunity("", "")
        )
    )

    fun removeCommunity(community: LemmyCommunity) {
        viewModelScope.launch {
            lemmyRepository.removeCommunity(community)
        }
    }

    fun getInfo(instance: String, community: String) {
        viewModelScope.launch {
            aboutCommutnityState = AboutCommunityState.Loading(LemmyCommunity(community, instance))
            val lemmyInfo = lemmyRepository.getCommunityInfo(LemmyCommunity(community, instance))
            if (lemmyInfo == null) {
                aboutCommutnityState =
                    AboutCommunityState.Error(LemmyCommunity(community, instance))
            } else {
                aboutCommutnityState = AboutCommunityState.Success(lemmyInfo)
                lemmyRepository.insertCommunity(lemmyInfo)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemerizeApplication)
                val lemmyRepository = application.container.lemmyCommunityRepository
                LemmyCommunityViewModel(lemmyRepository)
            }
        }
    }
}
