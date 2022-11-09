package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.Settings
import app.suhasdissa.memerize.navigateTo
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun PhotoView(photo: String, modifier: Modifier = Modifier) {
    val photoUrl = URLDecoder.decode(photo, StandardCharsets.UTF_8.toString())
    Column(modifier.fillMaxSize()) {
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
        Row(modifier.fillMaxWidth(),Arrangement.End) {
            IconButton(onClick = { }) {
                Icon(painter = painterResource(R.drawable.ic_download), contentDescription = "Download Photo",modifier.size(8.dp))
            }
        }
    }
}