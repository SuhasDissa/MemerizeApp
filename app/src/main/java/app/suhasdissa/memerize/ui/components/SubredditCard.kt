/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 1:22 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SubredditCardCompact(
    thumbnail: String?,
    title: String,
    onClickCard: () -> Unit,
    TrailingContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    Row(
        modifier
            .fillMaxWidth()
            .clickable {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onClickCard()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
            model = thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            Modifier
                .padding(8.dp)
        ) {
            TrailingContent()
        }
    }
}
