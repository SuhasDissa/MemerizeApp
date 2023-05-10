/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun HighlightCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    name: String,
    thumbnail: Int? = null,
    thumbnail_url: String? = null
) {
    ElevatedCard(
        modifier = modifier
            .width(300.dp)
            .height(128.dp)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onClick() })
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (thumbnail != null) {
                Image(
                    modifier = modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    painter = painterResource(thumbnail),
                    contentDescription = null
                )
            } else if (thumbnail_url != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).data(thumbnail_url)
                        .crossfade(true).build(),
                    contentDescription = null,
                    modifier = modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.reddit_placeholder),
                    placeholder = painterResource(R.drawable.reddit_placeholder)
                )
            }
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Preview()
@Composable
fun HighlightCardPreview() {
    HighlightCard(onClick = {}, name = "Preview", thumbnail = R.drawable.reddit_placeholder)
}