/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.model.SortTime
import app.suhasdissa.memerize.backend.viewmodels.RedditViewModel
import app.suhasdissa.memerize.backend.viewmodels.state.MemeUiState
import app.suhasdissa.memerize.ui.components.LoadingScreen
import app.suhasdissa.memerize.ui.components.MemeGrid
import app.suhasdissa.memerize.ui.components.RetryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedditMemeScreen(
    modifier: Modifier = Modifier,
    redditViewModel: RedditViewModel,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    redditViewModel.currentSubreddit?.let { "Reddit - $it" } ?: "Reddit",
                    color = MaterialTheme.colorScheme.primary
                )
            })
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            redditViewModel.currentSubreddit?.let {
                ElevatedCard(
                    modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                        OutlinedButton(onClick = {
                            redditViewModel.getMemePhotos(time = SortTime.TODAY)
                        }) {
                            Text(stringResource(R.string.reddit_today_btn))
                        }
                        OutlinedButton(onClick = {
                            redditViewModel.getMemePhotos(time = SortTime.WEEK)
                        }) {
                            Text(stringResource(R.string.reddit_week_btn))
                        }
                        OutlinedButton(onClick = {
                            redditViewModel.getMemePhotos(time = SortTime.MONTH)
                        }) {
                            Text(stringResource(R.string.reddit_month_btn))
                        }
                    }
                }
            }
            when (val memeDataState = redditViewModel.memeUiState) {
                is MemeUiState.Loading -> LoadingScreen(modifier)
                is MemeUiState.Error -> RetryScreen(
                    "Error Loading Online Memes",
                    "Show Offline Memes",
                    modifier,
                    onRetry = { redditViewModel.getLocalMemes() }
                )

                is MemeUiState.Success -> MemeGrid(
                    memeDataState.memes,
                    onClickMeme,
                    onClickVideo,
                    modifier
                )
            }
        }
    }
}
