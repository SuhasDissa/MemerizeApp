/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.viewmodels.RedditViewModel
import app.suhasdissa.memerize.backend.viewmodels.state.MemeUiState
import app.suhasdissa.memerize.ui.components.LoadingScreen
import app.suhasdissa.memerize.ui.components.MemeGrid
import app.suhasdissa.memerize.ui.components.RetryScreen
import app.suhasdissa.memerize.ui.components.SortBottomSheet
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedditMemeScreen(
    modifier: Modifier = Modifier,
    redditViewModel: RedditViewModel = viewModel(
        LocalContext.current as ComponentActivity,
        factory = RedditViewModel.Factory
    ),
    onClickCard: (Int) -> Unit
) {
    var showFilterButtons by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    redditViewModel.currentSubreddit?.let {
                        AsyncImage(
                            modifier = Modifier
                                .size(36.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(it.iconUrl).crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            stringResource(R.string.reddit)
                        )
                        redditViewModel.currentSubreddit?.let {
                            Text(it.name, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }, actions = {
                redditViewModel.currentSubreddit?.let {
                    IconButton(onClick = { showFilterButtons = !showFilterButtons }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = stringResource(R.string.filter_by_time)
                        )
                    }
                }
            })
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            when (val memeDataState = redditViewModel.memeUiState) {
                is MemeUiState.Loading -> LoadingScreen(modifier)
                is MemeUiState.Error -> RetryScreen(
                    stringResource(R.string.error_loading_online_memes),
                    stringResource(R.string.show_offline_memes),
                    modifier,
                    onRetry = { redditViewModel.getLocalMemes() }
                )

                is MemeUiState.Success -> MemeGrid(
                    memeDataState.memes,
                    onClickCard
                )
            }
        }
    }
    if (showFilterButtons) {
        SortBottomSheet(currentSort = redditViewModel.currentSortTime, onSelect = {
            showFilterButtons = false
            redditViewModel.getMemePhotos(sort = it)
        }, onDismissRequest = { showFilterButtons = false })
    }
}
