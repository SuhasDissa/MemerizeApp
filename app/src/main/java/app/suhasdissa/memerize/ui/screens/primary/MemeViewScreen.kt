package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.serializables.ChildData
import app.suhasdissa.memerize.backend.viewmodels.RedditViewModel
import app.suhasdissa.memerize.backend.viewmodels.UiState
import app.suhasdissa.memerize.ui.components.CardImage
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.LoadingScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MemeViewScreen(
    modifier: Modifier = Modifier,
    viewModel: RedditViewModel = viewModel(),
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    subreddit:String
) {
    fun refresh(time: String) {
        viewModel.getMemePhotos(subreddit, time)
    }
    LaunchedEffect(Unit) {
        refresh("today")
    }
    when (val memeUiState = viewModel.memeUiState) {
        is UiState.Loading -> LoadingScreen(modifier)
        is UiState.Error -> ErrorScreen(memeUiState.error, modifier)
        is UiState.Success -> MemeGrid(
            memeUiState, onClickMeme, onClickVideo, { time -> refresh(time) }, modifier
        )
        else -> {}
    }
}


@Composable
private fun MemeGrid(
    memeUiState: UiState.Success,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    refresh: (time: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(
            modifier
                .fillMaxWidth(.98f)
                .padding(10.dp)
        ) {
            Row(modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                OutlinedButton(onClick = { refresh("today") }) {
                    Text(stringResource(R.string.reddit_today_btn))
                }
                OutlinedButton(onClick = { refresh("week") }) {
                    Text(stringResource(R.string.reddit_week_btn))
                }
                OutlinedButton(onClick = { refresh("month") }) {
                    Text(stringResource(R.string.reddit_month_btn))
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(375.dp),
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            val photos = memeUiState.children.filter { it.Childdata.url.contains("i.redd.it") }
            val videos = memeUiState.children.filter { it.Childdata.url.contains("v.redd.it") }
            items(items = photos) { photo ->
                MemeCard(onClickMeme, photo.Childdata, modifier)
            }
            items(items = videos) { video ->
                VideoCard(onClickVideo, video.Childdata, modifier)
            }
        }


    }

}

@Composable
private fun MemeCard(
    onClickMeme: (url: String) -> Unit, photo: ChildData, modifier: Modifier = Modifier
) {
    val encodedImg = URLEncoder.encode(photo.url, StandardCharsets.UTF_8.toString())
    CardImage(modifier, onClickMeme, encodedImg, photo.url)
}

@Composable
private fun VideoCard(
    onClickVideo: (url: String) -> Unit, photo: ChildData, modifier: Modifier = Modifier
) {
    val vidlink = photo.secure_media?.reddit_video?.dash_url ?: return
    val encodedLink =
        URLEncoder.encode(vidlink.replace("&amp;", "&"), StandardCharsets.UTF_8.toString())
    val preview = photo.preview?.images?.get(0)?.source?.url ?: return


    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        CardImage(modifier, onClickVideo, encodedLink, preview.replace("&amp;", "&"))
        Card(shape = CircleShape) {
            Icon(
                modifier = modifier.size(70.dp),
                painter = painterResource(com.google.android.exoplayer2.R.drawable.exo_ic_play_circle_filled),
                contentDescription = stringResource(R.string.play_video_hint)
            )
        }
    }
}
