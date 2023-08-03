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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.backend.viewmodels.LemmyAboutState
import app.suhasdissa.memerize.backend.viewmodels.LemmyCommunityViewModel
import app.suhasdissa.memerize.ui.components.SubredditCardCompact
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    communityViewModel: LemmyCommunityViewModel = viewModel(
        factory = LemmyCommunityViewModel.Factory
    )
) {
    val communities by communityViewModel.communities.collectAsState()
    var subredditInfoSheet by remember { mutableStateOf(false) }
    var addNewDialog by remember { mutableStateOf(false) }
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = { addNewDialog = true }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add new subreddit")
        }
    }) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            items(items = communities) {
                SubredditCardCompact(
                    onClickCard = {
                        communityViewModel.getInfo(it.instance, it.community)
                        subredditInfoSheet = true
                    },
                    title = it.name,
                    thumbnail = it.iconUrl,
                    TrailingContent = {
                        IconButton(onClick = { communityViewModel.removeCommunity(it) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove subreddit"
                            )
                        }
                    }
                )
            }
        }
    }

    if (addNewDialog) {
        var newInstance by remember {
            mutableStateOf("")
        }
        var newCommunity by remember {
            mutableStateOf("")
        }
        AlertDialog(
            onDismissRequest = { addNewDialog = false },
            title = { Text("Add new Subreddit") },
            confirmButton = {
                Button(onClick = {
                    communityViewModel.getInfo(instance = newInstance, community = newCommunity)
                    addNewDialog = false
                    subredditInfoSheet = true
                }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(onClick = { addNewDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            text = {
                Column {
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        value = newInstance,
                        onValueChange = { newInstance = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.None
                        ),
                        prefix = { Text("https://") },
                        isError = (newInstance.contains(' ') || !newInstance.contains('.')),
                        label = { Text("Instance Url") },
                        placeholder = { Text("lemmy.ml") }
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        value = newCommunity,
                        onValueChange = { newCommunity = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.None
                        ),
                        isError = newInstance.contains(' '),
                        label = { Text("Community Name") },
                        placeholder = { Text("memes") }
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
                when (val state = communityViewModel.aboutState) {
                    is LemmyAboutState.Error -> {
                        Text(
                            text = "Failed to fetch Community info",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "${state.community}@${state.instance}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    is LemmyAboutState.Loading -> {
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
                            text = state.community,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "${state.community}@${state.instance}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    is LemmyAboutState.Success -> {
                        AsyncImage(
                            modifier = Modifier
                                .size(120.dp)
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(state.community.iconUrl).crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = state.community.name,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "${state.community.community}@${state.community.instance}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
