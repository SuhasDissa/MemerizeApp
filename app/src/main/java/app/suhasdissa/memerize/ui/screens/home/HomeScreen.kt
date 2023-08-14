/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 1:20 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.LibraryAddCheck
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.Destination
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.database.entity.RedditCommunity
import app.suhasdissa.memerize.backend.viewmodels.LemmyCommunityViewModel
import app.suhasdissa.memerize.backend.viewmodels.LemmyViewModel
import app.suhasdissa.memerize.backend.viewmodels.RedditCommunityViewModel
import app.suhasdissa.memerize.backend.viewmodels.RedditViewModel
import app.suhasdissa.memerize.ui.components.HighlightCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (Destination) -> Unit,
    onDrawerOpen: () -> Unit,
    redditCommunityViewModel: RedditCommunityViewModel = viewModel(
        factory = RedditCommunityViewModel.Factory
    ),
    lemmyCommunityViewModel: LemmyCommunityViewModel = viewModel(
        factory = LemmyCommunityViewModel.Factory
    ),
    redditViewModel: RedditViewModel,
    lemmyViewModel: LemmyViewModel
) {
    val subreddits by redditCommunityViewModel.communities.collectAsState()
    val communities by lemmyCommunityViewModel.communities.collectAsState()
    val selectedSubreddits = remember { mutableStateListOf<RedditCommunity>() }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
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
                stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.primary
            )
        }, actions = {
            if (selectedSubreddits.isNotEmpty()) {
                IconButton(onClick = {
                    redditViewModel.getMultiMemes(selectedSubreddits)
                    onNavigate(Destination.RedditMemeView)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.LibraryAddCheck,
                        contentDescription = stringResource(R.string.multi_reddit_feed)
                    )
                }
            }
        })
    }) { paddingValues ->
        LazyColumn(
            Modifier.fillMaxSize().padding(horizontal = 8.dp).padding(paddingValues)
        ) {
            item {
                Text(
                    stringResource(R.string.subreddits),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(items = subreddits) {
                HighlightCard(
                    onClick = {
                        if (selectedSubreddits.isNotEmpty()) {
                            if (selectedSubreddits.contains(it)) {
                                selectedSubreddits.remove(it)
                            } else {
                                selectedSubreddits.add(it)
                            }
                        } else {
                            redditViewModel.getMemePhotos(it)
                            onNavigate(Destination.RedditMemeView)
                        }
                    },
                    name = it.name,
                    thumbnail_url = it.iconUrl,
                    onLongClick = {
                        if (selectedSubreddits.contains(it)) {
                            selectedSubreddits.remove(it)
                        } else {
                            selectedSubreddits.add(it)
                        }
                    },
                    highlighted = selectedSubreddits.contains(it)
                )
            }
            item {
                Text(
                    stringResource(R.string.lemmy_communities),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(items = communities) {
                HighlightCard(
                    onClick = {
                        lemmyViewModel.getMemePhotos(it)
                        onNavigate(Destination.LemmyMemeView)
                    },
                    name = it.name,
                    thumbnail_url = it.iconUrl
                )
            }
        }
    }
}
