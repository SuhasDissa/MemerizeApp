/*******************************************************************************
Created By Suhas Dissanayake on 8/4/23, 11:03 AM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.suhasdissa.memerize.backend.viewmodels.LemmyViewModel
import app.suhasdissa.memerize.backend.viewmodels.state.MemeUiState
import app.suhasdissa.memerize.ui.components.LoadingScreen
import app.suhasdissa.memerize.ui.components.MemeGrid
import app.suhasdissa.memerize.ui.components.RetryScreen

@Composable
fun LemmyMemeScreen(
    modifier: Modifier = Modifier,
    lemmyViewModel: LemmyViewModel,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit
) {
    when (val memeDataState = lemmyViewModel.memeUiState) {
        is MemeUiState.Loading -> LoadingScreen(modifier)
        is MemeUiState.Error -> RetryScreen(
            "Error Loading Online Memes",
            "Show Offline Memes",
            modifier,
            onRetry = { lemmyViewModel.getLocalMemes() }
        )

        is MemeUiState.Success -> MemeGrid(
            memeDataState.memes,
            onClickMeme,
            onClickVideo,
            { time ->
                lemmyViewModel.getMemePhotos(time = time)
            },
            modifier
        )
    }
}
