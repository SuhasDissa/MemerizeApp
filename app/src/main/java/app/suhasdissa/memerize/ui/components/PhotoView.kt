package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
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
import app.suhasdissa.memerize.ui.utils.DownloadUtil
import app.suhasdissa.memerize.ui.utils.ShareUrl
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun PhotoView(photo: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val photoUrl = URLDecoder.decode(photo, StandardCharsets.UTF_8.toString())
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        Card(
            modifier
                .padding(10.dp)
                .fillMaxWidth(.98f)
        ) {
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

                IconButton(onClick = { DownloadUtil(context, photoUrl) }, modifier) {
                    Icon(
                        painter = painterResource(R.drawable.ic_download),
                        contentDescription = "Download Photo"
                    )
                }
                IconButton(onClick = {
                    ShareUrl(context, photoUrl)
                }, modifier) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = "Share Photo"
                    )
                }
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photoUrl)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.meme_photo),
            contentScale = ContentScale.Fit,
            modifier = modifier.fillMaxSize(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )

    }
}