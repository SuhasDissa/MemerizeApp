/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.ui.components.HighlightCard

data class HighlightCardItem(val name: String, val category: String, val thumbnail: String)

val redditList = listOf(
    HighlightCardItem(
        "TKasylum",
        "tkasylum",
        "https://styles.redditmedia.com/t5_51onzf/styles/communityIcon_mecdz2ktyk481.jpg"
    ),
    HighlightCardItem(
        "PieFM",
        "piefm",
        "https://styles.redditmedia.com/t5_w2oen/styles/communityIcon_q818qhbawip31.png"
    ),
    HighlightCardItem(
        "Maybe Maybe",
        "maybemaybemaybe",
        "https://styles.redditmedia.com/t5_38e1l/styles/communityIcon_hcpveq6pu5p41.png"
    ),
    HighlightCardItem(
        "HolUP",
        "holup",
        "https://styles.redditmedia.com/t5_qir9n/styles/communityIcon_yvasg0bnblaa1.png"
    ),
    HighlightCardItem(
        "Funny",
        "funny",
        "https://a.thumbs.redditmedia.com/kIpBoUR8zJLMQlF8azhN-kSBsjVUidHjvZNLuHDONm8.png"
    ),
    HighlightCardItem(
        "FacePalm",
        "facepalm",
        "https://styles.redditmedia.com/t5_2r5rp/styles/communityIcon_qzjxzx1g08z91.jpg"
    ),
    HighlightCardItem(
        "Memes",
        "memes",
        "https://styles.redditmedia.com/t5_2qjpg/styles/communityIcon_uzvo7sibvc3a1.jpg"
    ),
    HighlightCardItem(
        "Dank Memes",
        "dankmemes",
        "https://styles.redditmedia.com/t5_2zmfe/styles/communityIcon_g5xoywnpe2l91.png"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickSettings: () -> Unit,
    onClickMemeView: (category: String) -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(stringResource(R.string.app_name)) }, actions = {
            IconButton(onClick = { onClickSettings() }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        })
    }) { innerPadding ->
        LazyColumn(
            modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.reddit_memes),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            item {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(4),
                    modifier = modifier
                        .fillMaxWidth()
                        .height(560.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(items = redditList) { card ->
                        HighlightCard(
                            onClick = { onClickMemeView(card.category) },
                            modifier = modifier,
                            name = card.name,
                            thumbnail_url = card.thumbnail
                        )
                    }
                }
            }
        }
    }
}
