/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.backend.viewmodels.FeedViewModel
import app.suhasdissa.memerize.backend.viewmodels.PostsState
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.LoadingScreen
import app.suhasdissa.memerize.ui.components.TextCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    feedViewModel: FeedViewModel = viewModel(),
    onClickTextCard: (url: String) -> Unit
) {
    when (val postsState = feedViewModel.state) {
        is PostsState.Loading -> LoadingScreen(modifier)
        is PostsState.Error -> ErrorScreen(postsState.error, modifier)
        is PostsState.Success -> FeedGrid(
            postsState, modifier, onClickTextCard
        )
    }

}

@Composable
private fun FeedGrid(
    postsState: PostsState.Success,
    modifier: Modifier = Modifier,
    onClickTextCard: (url: String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(375.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = postsState.children) { item ->
            val encodedText = URLEncoder.encode(
                item.content.replace("<br>", "\n"),
                StandardCharsets.UTF_8.toString()
            )
            TextCard(clickAction = { onClickTextCard(encodedText) }, text = item.title)
        }

    }
}