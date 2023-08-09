/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.model.SortTime
import app.suhasdissa.memerize.backend.viewmodels.RedditViewModel
import app.suhasdissa.memerize.backend.viewmodels.state.MemeUiState
import app.suhasdissa.memerize.ui.components.LoadingScreen
import app.suhasdissa.memerize.ui.components.MemeGrid
import app.suhasdissa.memerize.ui.components.RetryScreen
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedditMemeScreen(
    modifier: Modifier = Modifier,
    redditViewModel: RedditViewModel,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit
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
                            "Reddit"
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
                            contentDescription = "Filter by time"
                        )
                    }
                }
            })
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            redditViewModel.currentSubreddit?.let {
                AnimatedVisibility(visible = showFilterButtons) {
                    Row(modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                        FilterChip(
                            selected = redditViewModel.currentSortTime == SortTime.TODAY,
                            onClick = {
                                showFilterButtons = false
                                redditViewModel.getMemePhotos(time = SortTime.TODAY)
                            },
                            label = {
                                Text(stringResource(R.string.reddit_today_btn))
                            }
                        )
                        FilterChip(
                            selected = redditViewModel.currentSortTime == SortTime.WEEK,
                            onClick = {
                                showFilterButtons = false
                                redditViewModel.getMemePhotos(time = SortTime.WEEK)
                            },
                            label = {
                                Text(stringResource(R.string.reddit_week_btn))
                            }
                        )
                        FilterChip(
                            selected = redditViewModel.currentSortTime == SortTime.MONTH,
                            onClick = {
                                showFilterButtons = false
                                redditViewModel.getMemePhotos(time = SortTime.MONTH)
                            },
                            label = {
                                Text(stringResource(R.string.reddit_month_btn))
                            }
                        )
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
                    onClickVideo
                )
            }
        }
    }
}
