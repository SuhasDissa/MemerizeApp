/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CardImage(
    modifier: Modifier = Modifier, clickAction: () -> Unit, photoUrl: String, title: String
) {
    ElevatedCard(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { clickAction() }) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, modifier = Modifier.padding(horizontal = 8.dp), style = MaterialTheme.typography.titleLarge,color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(photoUrl)
                    .crossfade(true).build(),
                contentDescription = stringResource(R.string.meme_photo),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img)
            )
        }
    }
}