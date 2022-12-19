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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.backend.repositories.Meme
import app.suhasdissa.memerize.backend.viewmodels.TelegramUiState
import app.suhasdissa.memerize.backend.viewmodels.TelegramViewModel
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.LoadingScreen


@Composable
fun TelegramMemeScreen(
    modifier: Modifier = Modifier,
    viewModel: TelegramViewModel = viewModel(factory = TelegramViewModel.Factory),
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    channel: String
) {
    var _channel: String by rememberSaveable { mutableStateOf("") }

    if (_channel != channel) {
        LaunchedEffect(Unit) {
            viewModel.getMemePhotos(channel)
            _channel = channel
        }
    }
    when (val memeUiState = viewModel.state) {
        is TelegramUiState.Loading -> LoadingScreen(modifier)
        is TelegramUiState.Error -> ErrorScreen(memeUiState.error, modifier)
        is TelegramUiState.Success -> MemeGrid(
            memeUiState.memes, onClickMeme, onClickVideo, modifier
        )
    }

}


@Composable
private fun MemeGrid(
    memes: List<Meme>,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (memes.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(375.dp),
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(4.dp)
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