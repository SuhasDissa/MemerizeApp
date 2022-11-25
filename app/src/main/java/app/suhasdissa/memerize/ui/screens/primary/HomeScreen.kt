/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.ui.components.HighlightCard

data class HighlightCardItem(val name: Int, val category: String, val thumbnail: Int)

val redditList = listOf(
    HighlightCardItem(R.string.reddit_tkasylum, "tkasylum", R.drawable.tkasylum),
    HighlightCardItem(R.string.reddit_piefm, "piefm", R.drawable.piefm),
    HighlightCardItem(R.string.reddit_rmemes, "memes", R.drawable.memes),
    HighlightCardItem(R.string.reddit_dankmemes, "dankmemes", R.drawable.dankmemes)
)
val telegramList = listOf(
    HighlightCardItem(R.string.telegram_chaplin, "chap_lin_sl", R.drawable.chap_lin_sl),
    HighlightCardItem(R.string.telegram_eplussl, "eplussl", R.drawable.eplussl)
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickMemeView: (category: String) -> Unit,
    onClickFunnyVideo: () -> Unit,
    onClickFeed: () -> Unit,
    onClickTG: (category: String) -> Unit
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .padding(10.dp), verticalArrangement = Arrangement.spacedBy(20.dp)
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