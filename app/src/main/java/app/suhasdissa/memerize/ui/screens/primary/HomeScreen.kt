/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.ui.components.HighlightCard

data class HighlightCardItem(val name: Int, val category: String, val thumbnail: Int)

val redditList = listOf(
    HighlightCardItem(R.string.reddit_tkasylum, "tkasylum", R.drawable.tkasylum),
    HighlightCardItem(R.string.reddit_piefm, "piefm", R.drawable.piefm),
    HighlightCardItem(R.string.reddit_maybemaybe, "maybemaybemaybe", R.drawable.maybe),
    HighlightCardItem(R.string.reddit_rmemes, "memes", R.drawable.memes),
    HighlightCardItem(R.string.reddit_dankmemes, "dankmemes", R.drawable.dankmemes)
)
val telegramList = listOf(
    HighlightCardItem(R.string.telegram_chaplin, "chap_lin_sl", R.drawable.chap_lin_sl),
    HighlightCardItem(R.string.telegram_eplussl, "eplussl", R.drawable.eplussl)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickSettings: () -> Unit,
    onClickMemeView: (category: String) -> Unit,
    onClickFunnyVideo: () -> Unit,
    onClickFeed: () -> Unit,
    onClickTG: (category: String) -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(stringResource(R.string.app_name)) }, actions = {
            IconButton(onClick = { onClickSettings() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_setting),
                    contentDescription = "Settings"
                )
            }
        })
    }) { innerPadding ->
        LazyColumn(
            modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.reddit_memes),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            item {
                LazyRow(modifier = modifier.fillMaxWidth(), contentPadding = PaddingValues(4.dp)) {
                    items(items = redditList) { card ->
                        HighlightCard(
                            onClick = { onClickMemeView(card.category) },
                            modifier = modifier,
                            name = card.name,
                            thumbnail = card.thumbnail
                        )

                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.telegram_memes),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            item {
                LazyRow(modifier = modifier.fillMaxWidth(), contentPadding = PaddingValues(4.dp)) {
                    items(items = telegramList) { card ->
                        HighlightCard(
                            onClick = { onClickTG(card.category) },
                            modifier = modifier,
                            name = card.name,
                            thumbnail = card.thumbnail
                        )

                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.funny_stuff_category),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            item {
                LazyRow(modifier = modifier.fillMaxWidth(), contentPadding = PaddingValues(4.dp)) {
                    item {
                        HighlightCard(
                            onClick = onClickFunnyVideo,
                            modifier = modifier,
                            name = R.string.funny_videos,
                            thumbnail = R.drawable.ic_launcher_foreground
                        )
                    }
                    item {
                        HighlightCard(
                            onClick = onClickFeed,
                            modifier = modifier,
                            name = R.string.funny_posts,
                            thumbnail = R.drawable.ic_launcher_foreground
                        )
                    }
                }
            }
        }
    }
}