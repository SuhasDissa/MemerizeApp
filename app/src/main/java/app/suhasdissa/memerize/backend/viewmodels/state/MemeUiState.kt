/*******************************************************************************
Created By Suhas Dissanayake on 8/4/23, 12:13 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.viewmodels.state

import app.suhasdissa.memerize.backend.database.entity.Meme

sealed interface MemeUiState {
    data class Success(val memes: List<Meme>) : MemeUiState
    data class Error(val error: String) : MemeUiState
    object Loading : MemeUiState
}
