package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.UiState
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MemeViewScreen(
    memeUiState: UiState,
    onClickMeme: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (memeUiState) {
        is UiState.Loading -> LoadingScreen(modifier)
        is UiState.Error -> ErrorScreen(memeUiState.error, modifier)

        is UiState.Success -> LazyVerticalGrid(
            columns = GridCells.Adaptive(375.dp),
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            val filtered = memeUiState.children.filter { it.Childdata.url.contains("i.redd.it") }
            items(items = filtered) { photo ->
                ElevatedCard(modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { onClickMeme("TestMessage") }) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(photo.Childdata.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.meme_photo),
                        contentScale = ContentScale.FillBounds,
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img)
                    )
                }
            }
        }

    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(memeUiState: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
        //Text(memeUiState, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun MemeScreen(photo: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
       /* AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.meme_photo),
            contentScale = ContentScale.FillBounds,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )*/
        Text(photo)
    }
}
