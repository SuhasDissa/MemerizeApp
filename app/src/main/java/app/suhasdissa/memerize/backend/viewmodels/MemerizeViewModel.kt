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
import app.suhasdissa.memerize.backend.apis.MemerizeApi
import app.suhasdissa.memerize.backend.serializables.MemerizeModel
import kotlinx.coroutines.launch

sealed interface FunnyVideoState {
    data class Success(val children: List<MemerizeModel>) : FunnyVideoState
    data class Error(val error: String) : FunnyVideoState
    object Loading : FunnyVideoState
}

class VideoViewModel : ViewModel() {
    var state: FunnyVideoState by mutableStateOf(FunnyVideoState.Loading)

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            state = FunnyVideoState.Loading
            state = try {
                FunnyVideoState.Success(
                    MemerizeApi.retrofitService.getData("videos")
                )
            } catch (e: Exception) {
                FunnyVideoState.Error(e.toString())
            }
        }
    }
}

sealed interface PostsState {
    data class Success(val children: List<MemerizeModel>) : PostsState
    data class Error(val error: String) : PostsState
    object Loading : PostsState
}

class FeedViewModel : ViewModel() {
    var state: PostsState by mutableStateOf(PostsState.Loading)
        private set

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            state = PostsState.Loading
            state = try {
                PostsState.Success(
                    MemerizeApi.retrofitService.getData("posts")
                )
            } catch (e: Exception) {
                PostsState.Error(e.toString())
            }
        }
    }
}