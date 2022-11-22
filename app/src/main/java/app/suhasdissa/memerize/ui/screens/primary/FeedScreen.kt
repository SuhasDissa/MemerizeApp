package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.backend.FeedViewModel
import app.suhasdissa.memerize.backend.PostsState
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.FeedCard
import app.suhasdissa.memerize.ui.components.LoadingScreen

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier, feedViewModel: FeedViewModel = viewModel()
) {
    when (val postsState = feedViewModel.state) {
        is PostsState.Loading -> LoadingScreen(modifier)
        is PostsState.Error -> ErrorScreen(postsState.error, modifier)
        is PostsState.Success -> FeedGrid(
            postsState, modifier
        )
        else -> {}
    }

}

@Composable
private fun FeedGrid(
    postsState: PostsState.Success, modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(375.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = postsState.children) { item ->
            FeedCard(topic = item.title, content = item.content.replace("<br>", "\n"))
        }

    }
}