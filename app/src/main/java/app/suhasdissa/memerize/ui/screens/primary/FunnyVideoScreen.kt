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
import app.suhasdissa.memerize.backend.viewmodels.FunnyVideoState
import app.suhasdissa.memerize.backend.viewmodels.VideoViewModel
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.LoadingScreen
import app.suhasdissa.memerize.ui.components.TextCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FunnyVideoScreen(
    modifier: Modifier = Modifier, videoViewModel: VideoViewModel = viewModel(),
    onClickTextCard: (url: String) -> Unit
) {

    when (val funnyVideoState = videoViewModel.state) {
        is FunnyVideoState.Loading -> LoadingScreen(modifier)
        is FunnyVideoState.Error -> ErrorScreen(funnyVideoState.error, modifier)
        is FunnyVideoState.Success -> TextCardGrid(
            funnyVideoState, onClickTextCard, modifier
        )
        else -> {}
    }

}

@Composable
private fun TextCardGrid(
    funnyVideoState: FunnyVideoState.Success,
    onClickTextCard: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(375.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = funnyVideoState.children) { item ->
            val encodedURl = URLEncoder.encode(item.content, StandardCharsets.UTF_8.toString())
            TextCard(clickAction = onClickTextCard, text = item.title, clickUrl = encodedURl)
        }

    }
}