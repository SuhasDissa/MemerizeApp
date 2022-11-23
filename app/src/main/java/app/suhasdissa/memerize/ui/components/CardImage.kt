/*******************************************************************************
 Created By Suhas Dissanayake on 11/23/22, 4:16 PM
 Copyright (c) 2022
 https://github.com/SuhasDissa/
 All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CardImage(
    modifier: Modifier = Modifier,
    clickAction: (url: String) -> Unit,
    clickUrl: String,
    photoUrl: String
) {
    ElevatedCard(modifier = modifier
        .padding(4.dp)
        .fillMaxWidth()
        .aspectRatio(1f)
        .clickable { clickAction(clickUrl) }) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photoUrl)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.meme_photo),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )
    }
}