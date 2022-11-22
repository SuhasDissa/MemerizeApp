package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.TelegramUiState
import app.suhasdissa.memerize.backend.TelegramViewModel
import app.suhasdissa.memerize.ui.components.CardImage
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.LoadingScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun TelegramMemeScreen(
    modifier: Modifier = Modifier,
    viewModel: TelegramViewModel = viewModel(),
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit
) {
    when (val memeUiState = viewModel.state) {
        is TelegramUiState.Loading -> LoadingScreen(modifier)
        is TelegramUiState.Error -> ErrorScreen(memeUiState.error, modifier)
        is TelegramUiState.Success -> MemeGrid(
            memeUiState, onClickMeme, onClickVideo, modifier
        )
        else -> {}
    }

}


@Composable
private fun MemeGrid(
    memeUiState: TelegramUiState.Success,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(375.dp),
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            val photos = memeUiState.messages.filter {
                it.media?.type?.contains("messageMediaPhoto") ?: false
            }
            val videos = memeUiState.messages.filter {
                it.media?.type?.contains("messageMediaDocument") ?: false
            }
            items(items = photos) { photo ->
                MemeCard(onClickMeme, photo.id, modifier)
            }
            items(items = videos) { video ->
                VideoCard(onClickVideo, video.id, modifier)
            }
        }


    }

}

@Composable
private fun MemeCard(
    onClickMeme: (url: String) -> Unit, photoid: Int, modifier: Modifier = Modifier
) {
    val url = "https://tg.i-c-a.su/media/chap_lin_sl/$photoid"
    val encodedImg = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    CardImage(modifier, onClickMeme, encodedImg, url)
}

@Composable
private fun VideoCard(
    onClickVideo: (url: String) -> Unit, videoid: Int, modifier: Modifier = Modifier
) {
    val url = "https://tg.i-c-a.su/media/chap_lin_sl/$videoid"
    val encodedLink = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    val preview = "https://tg.i-c-a.su/media/chap_lin_sl/$videoid/preview"
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        CardImage(modifier, onClickVideo, encodedLink, preview)
        Card(shape = CircleShape) {
            Icon(
                modifier = modifier.size(70.dp),
                painter = painterResource(com.google.android.exoplayer2.R.drawable.exo_ic_play_circle_filled),
                contentDescription = stringResource(R.string.play_video_hint)
            )
        }
    }
}
