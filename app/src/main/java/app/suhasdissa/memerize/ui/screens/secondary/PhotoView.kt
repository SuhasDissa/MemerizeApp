/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

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
import app.suhasdissa.memerize.utils.downloadUtil
import app.suhasdissa.memerize.utils.shareUrl
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun PhotoView(photo: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val photoUrl = URLDecoder.decode(photo, StandardCharsets.UTF_8.toString())
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photoUrl)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.meme_photo),
            contentScale = ContentScale.Fit,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )

        Row(
            modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(onClick = { downloadUtil(context, photoUrl) }, modifier) {
                Icon(
                    painter = painterResource(R.drawable.ic_download),
                    contentDescription = "Download Photo",
                    modifier.size(40.dp)
                )
            }
            IconButton(onClick = {
                shareUrl(context, photoUrl)
            }, modifier) {
                Icon(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = "Share Photo",
                    modifier.size(40.dp)
                )
            }
        }


    }
}