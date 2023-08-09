/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HighlightCard(
    onClick: () -> Unit,
    name: String,
    thumbnail_url: String? = null,
    highlighted: Boolean = false,
    onLongClick: () -> Unit = {}
) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(8.dp),
        colors = if (highlighted) {
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            CardDefaults.elevatedCardColors()
        }
    ) {
        Row(
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onClick.invoke()
                    },
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onLongClick.invoke()
                    }
                )
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (thumbnail_url != null) {
                AsyncImage(
                    model = thumbnail_url,
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .aspectRatio(1f)
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
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview()
@Composable
fun HighlightCardPreview() {
    HighlightCard(onClick = {}, name = "Preview")
}
