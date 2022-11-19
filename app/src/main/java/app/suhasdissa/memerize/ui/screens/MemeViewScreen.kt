package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.backend.ChildData
import app.suhasdissa.memerize.backend.ImageViewModel
import app.suhasdissa.memerize.backend.UiState
import app.suhasdissa.memerize.ui.components.CardImage
import app.suhasdissa.memerize.ui.components.ErrorScreen
import app.suhasdissa.memerize.ui.components.LoadingScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MemeViewScreen(
    modifier: Modifier = Modifier,
    viewModel: ImageViewModel = viewModel(),
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit
) {
    when (val memeUiState = viewModel.memeUiState) {
        is UiState.Loading -> LoadingScreen(modifier)
        is UiState.Error -> ErrorScreen(memeUiState.error, modifier)
        is UiState.Success -> MemeGrid(
            memeUiState,
            onClickMeme,
            onClickVideo,
            viewModel::getMemePhotos,
            modifier
        )
    }

}


@Composable
private fun MemeGrid(
    memeUiState: UiState.Success,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    refresh: (subreddit: String, time: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(
            modifier
                .fillMaxWidth(.98f)
                .padding(10.dp)
        ) {
            Row(modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                OutlinedButton(onClick = { refresh("tkasylum", "today") }) {
                    Text("Today")
                }
                OutlinedButton(onClick = { refresh("tkasylum", "week") }) {
                    Text("This Week")
                }
                OutlinedButton(onClick = { refresh("tkasylum", "month") }) {
                    Text("This Month")
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
    onClickMeme: (url: String) -> Unit,
    photo: ChildData,
    modifier: Modifier = Modifier
) {
    val encodedImg = URLEncoder.encode(photo.url, StandardCharsets.UTF_8.toString())
    CardImage(modifier, onClickMeme, encodedImg, photo.url)
}

@Composable
private fun VideoCard(
    onClickVideo: (url: String) -> Unit, photo: ChildData, modifier: Modifier = Modifier
) {
    /*val vidid = photo.permalink?.split("/")?.slice(4..5)?.joinToString("/") ?: return
    val vidlink = "https://www.redditmedia.com/mediaembed/$vidid"*/

    val vidlink = photo.secure_media?.reddit_video?.dash_url ?: return
    val encodedLink = URLEncoder.encode(vidlink.replace("&amp;", "&"), StandardCharsets.UTF_8.toString())
    val preview = photo.preview?.images?.get(0)?.source?.url ?: return


    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        CardImage(modifier, onClickVideo, encodedLink, preview.replace("&amp;", "&"))
        Icon(
            painter = painterResource(com.google.android.exoplayer2.R.drawable.exo_ic_play_circle_filled),
            contentDescription = "Download Photo"
        )
    }
}
