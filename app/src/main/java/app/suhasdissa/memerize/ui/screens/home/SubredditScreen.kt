/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 1:20 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.viewmodels.RedditCommunityViewModel
import app.suhasdissa.memerize.backend.viewmodels.state.AboutCommunityState
import app.suhasdissa.memerize.ui.components.SubredditCardCompact
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubredditScreen(

    onDrawerOpen: () -> Unit,
    redditCommunityViewModel: RedditCommunityViewModel = viewModel(
        factory = RedditCommunityViewModel.Factory
    )
) {
    val subreddits by redditCommunityViewModel.communities.collectAsState()
    var subredditInfoSheet by remember { mutableStateOf(false) }
    var addNewDialog by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { addNewDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new_subreddit)
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(navigationIcon = {
                IconButton(onClick = {
                    onDrawerOpen.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.open_navigation_drawer)
                    )
                }
            }, title = {
                Text(
                    "Subreddits",
                    color = MaterialTheme.colorScheme.primary
                )
            })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            items(items = subreddits) {
                SubredditCardCompact(
                    onClickCard = {
                        redditCommunityViewModel.getSubredditInfo(it.id)
                        subredditInfoSheet = true
                    },
                    title = it.name,
                    thumbnail = it.iconUrl,
                    TrailingContent = {
                        IconButton(onClick = { redditCommunityViewModel.removeSubreddit(it) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.remove_subreddit)
                            )
                        }
                    }
                )
            }
        }
    }

    if (addNewDialog) {
        var newSubreddit by remember {
            mutableStateOf("")
        }
        AlertDialog(
            onDismissRequest = { addNewDialog = false },
            title = { Text(stringResource(R.string.add_new_subreddit)) },
            confirmButton = {
                Button(onClick = {
                    redditCommunityViewModel.getSubredditInfo(newSubreddit)
                    addNewDialog = false
                    subredditInfoSheet = true
                }) {
                    Text(text = stringResource(R.string.save))
                }
            },
            dismissButton = {
                Button(onClick = { addNewDialog = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            text = {
                Column {
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        value = newSubreddit,
                        onValueChange = { newSubreddit = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.None
                        ),
                        prefix = { Text("r/") },
                        isError = newSubreddit.contains(' '),
                        label = { Text(stringResource(R.string.subreddit_name_from_url)) },
                        placeholder = { Text("maybemaybemaybe") }
                    )
                }
            }
        )
    }

    if (subredditInfoSheet) {
        ModalBottomSheet(onDismissRequest = { subredditInfoSheet = false }) {
            Column(
                Modifier.fillMaxWidth().padding(bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (val state = redditCommunityViewModel.aboutCommunityState) {
                    is AboutCommunityState.Error -> {
                        Text(
                            text = stringResource(R.string.failed_to_fetch_subreddit_info),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "r/${state.community.id}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    is AboutCommunityState.Loading -> {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .size(120.dp)
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }

                        Text(
                            text = state.community.name,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "r/${state.community.id}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    is AboutCommunityState.Success -> {
                        AsyncImage(
                            modifier = Modifier
                                .size(120.dp)
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            model = state.community.iconUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = state.community.name,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "r/${state.community.id}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
