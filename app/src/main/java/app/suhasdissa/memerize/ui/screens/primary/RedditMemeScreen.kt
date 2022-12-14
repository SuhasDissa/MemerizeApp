/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.repositories.Meme
import app.suhasdissa.memerize.backend.viewmodels.RedditViewModel
import app.suhasdissa.memerize.backend.viewmodels.UiState
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.LoadingScreen
import app.suhasdissa.memerize.ui.components.MemeCard
import app.suhasdissa.memerize.ui.components.VideoCard


@Composable
fun RedditMemeScreen(
    modifier: Modifier = Modifier,
    viewModel: RedditViewModel = viewModel(factory = RedditViewModel.Factory),
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    subreddit: String
) {
    fun refresh(time: String) {
        viewModel.getMemePhotos(subreddit, time)
    }

    var _subreddit: String by rememberSaveable { mutableStateOf("") }

    if (_subreddit != subreddit) {
        LaunchedEffect(Unit) {
            refresh("today")
            _subreddit = subreddit
        }
    }
    when (val memeUiState = viewModel.memeUiState) {
        is UiState.Loading -> LoadingScreen(modifier)
        is UiState.Error -> ErrorScreen(memeUiState.error, modifier)
        is UiState.Success -> MemeGrid(
            memeUiState.memes, onClickMeme, onClickVideo, { time -> refresh(time) }, modifier
        )
    }
}


@Composable
private fun MemeGrid(
    memes: List<Meme>,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    refresh: (time: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                OutlinedButton(onClick = { refresh("today") }) {
                    Text(stringResource(R.string.reddit_today_btn))
                }
                OutlinedButton(onClick = { refresh("week") }) {
                    Text(stringResource(R.string.reddit_week_btn))
                }
                OutlinedButton(onClick = { refresh("month") }) {
                    Text(stringResource(R.string.reddit_month_btn))
                }
            }
        }
        if (memes.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(375.dp),
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp)
            ) {

                items(items = memes) { meme ->
                    if (meme.isVideo) {
                        VideoCard(onClickVideo, meme.url, meme.preview, modifier)
                    } else {
                        MemeCard(onClickMeme, meme.url, modifier)
                    }
                }
            }
        } else {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "No Memes Here",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

    }

}