package app.suhasdissa.memerize.ui.screens

import android.print.PrintAttributes.Margins
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.ChildData
import app.suhasdissa.memerize.backend.UiState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MemeViewScreen(
    refresh: (subreddit: String, time: String) -> Unit,
    memeUiState: UiState,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (memeUiState) {
        is UiState.Loading -> LoadingScreen(modifier)
        is UiState.Error -> ErrorScreen(memeUiState.error, modifier)

        is UiState.Success -> MemeGrid(memeUiState, onClickMeme, onClickVideo,refresh, modifier)
    }

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(memeUiState: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
        //Text(memeUiState, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun MemeGrid(
    memeUiState: UiState.Success,
    onClickMeme: (url: String) -> Unit,
    onClickVideo: (url: String) -> Unit,
    refresh: (subreddit: String, time: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(modifier.fillMaxWidth(.95f)) {
            Row(modifier.fillMaxWidth(),Arrangement.SpaceEvenly) {
                OutlinedButton(onClick = {refresh("tkasylum","today")}) {
                    Text("Today")
                }
                OutlinedButton(onClick = {refresh("tkasylum","week")}) {
                    Text("This Week")
                }
                OutlinedButton(onClick = { refresh("tkasylum","month") }) {
                    Text("This Month")
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(375.dp),
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            val photos =
                memeUiState.children.filter { it.Childdata.url.contains("i.redd.it") }
            val videos =
                memeUiState.children.filter { it.Childdata.url.contains("v.redd.it") }
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
fun MemeCard(onClickMeme: (url: String) -> Unit, photo: ChildData, modifier: Modifier = Modifier) {
    val encodedImg = URLEncoder.encode(photo.url, StandardCharsets.UTF_8.toString())

    ElevatedCard(modifier = modifier
        .padding(4.dp)
        .fillMaxWidth()
        .aspectRatio(1f)
        .clickable { onClickMeme(encodedImg) }) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photo.url)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.meme_photo),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )
    }
}

@Composable
fun VideoCard(
    onClickVideo: (url: String) -> Unit,
    photo: ChildData,
    modifier: Modifier = Modifier
) {
    val vidid = photo.permalink?.split("/")?.slice(4..5)?.joinToString("/") ?: return
    val vidlink = "https://www.redditmedia.com/mediaembed/$vidid"

    val preview = photo.preview?.images?.get(0)?.source?.url ?: return
    val encodedLink = URLEncoder.encode(vidlink, StandardCharsets.UTF_8.toString())

    ElevatedCard(modifier = modifier
        .padding(4.dp)
        .fillMaxWidth()
        .aspectRatio(1f)
        .clickable { onClickVideo(encodedLink) }) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(preview)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.meme_photo),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )
    }
}


@Composable
fun MemeScreen(photo: String, modifier: Modifier = Modifier) {
    val photoUrl = URLDecoder.decode(photo,StandardCharsets.UTF_8.toString())
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(photoUrl).crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.meme_photo),
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize(),
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img)
    )
}
