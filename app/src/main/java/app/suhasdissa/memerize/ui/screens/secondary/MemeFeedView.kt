/*******************************************************************************
Created By Suhas Dissanayake on 8/15/23, 12:48 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.secondary

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.backend.database.entity.Meme
import app.suhasdissa.memerize.backend.viewmodels.LemmyViewModel
import app.suhasdissa.memerize.backend.viewmodels.RedditViewModel
import app.suhasdissa.memerize.backend.viewmodels.state.MemeUiState

@Composable
fun RedditMemeFeed(
    initialPage: Int,
    redditViewModel: RedditViewModel = viewModel(
        LocalContext.current as ComponentActivity,
        factory = RedditViewModel.Factory
    )
) {
    when (val state = redditViewModel.memeUiState) {
        is MemeUiState.Success -> MemeFeedView(initialPage, memes = state.memes)
        else -> {}
    }
}

@Composable
fun LemmyMemeFeed(
    initialPage: Int,
    lemmyViewModel: LemmyViewModel = viewModel(
        LocalContext.current as ComponentActivity,
        factory = LemmyViewModel.Factory
    )
) {
    when (val state = lemmyViewModel.memeUiState) {
        is MemeUiState.Success -> MemeFeedView(initialPage, memes = state.memes)
        else -> {}
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MemeFeedView(initialPage: Int, memes: List<Meme>) {
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        initialPageOffsetFraction = 0f
    ) {
        memes.size
    }
    VerticalPager(modifier = Modifier.fillMaxSize(), state = pagerState) {
        with(memes[it]) {
            if (isVideo) {
                VideoView(this, playWhenReady = (it == pagerState.currentPage))
            } else {
                PhotoView(this)
            }
        }
    }
}
