package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.backend.MemerizeUiState
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.LoadingScreen
import app.suhasdissa.memerize.ui.components.TextCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FunnyVideoScreen(
    memerizeUiState: MemerizeUiState,
    onClickTextCard: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (memerizeUiState) {
        is MemerizeUiState.Loading -> LoadingScreen(modifier)
        is MemerizeUiState.Error -> ErrorScreen(memerizeUiState.error, modifier)
        is MemerizeUiState.Success -> TextCardGrid(
            memerizeUiState, onClickTextCard, modifier
        )
    }

}

@Composable
fun TextCardGrid(
    memerizeUiState: MemerizeUiState.Success,
    onClickTextCard: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(375.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = memerizeUiState.children) { item ->
            val encodedURl = URLEncoder.encode(item.link, StandardCharsets.UTF_8.toString())
            TextCard(clickAction = onClickTextCard, text = item.title, clickUrl = encodedURl)
        }

    }
}