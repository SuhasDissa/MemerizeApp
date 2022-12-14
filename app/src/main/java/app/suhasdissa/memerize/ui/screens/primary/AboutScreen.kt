/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.primary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.utils.checkUpdate
import app.suhasdissa.memerize.utils.openBrowser
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            stringResource(R.string.developer_heading)
        )
        Card(modifier.fillMaxWidth()) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = modifier
                        .size(128.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(stringResource(R.string.github_avatar)).crossfade(true).build(),
                    contentDescription = stringResource(R.string.avatar_description)
                )
                Column(
                    modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.developer_name)
                    )
                    Text(
                        text = stringResource(R.string.developer_username)
                    )
                    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        IconButton(onClick = {
                            openBrowser(
                                context, "https://github.com/SuhasDissa"
                            )
                        }) {

                            Icon(
                                painterResource(R.drawable.ic_github),
                                contentDescription = stringResource(R.string.github_icon_hint),
                                modifier.size(48.dp)
                            )
                        }
                        IconButton(onClick = {
                            openBrowser(
                                context, "https://twitter.com/SuhasDissa"
                            )
                        }) {

                            Icon(
                                painterResource(R.drawable.ic_twitter),
                                contentDescription = stringResource(R.string.twitter_icon_hint),
                                modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }
        Card(
            modifier.fillMaxWidth()
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.update_description)
                )
                Button(onClick = { checkUpdate(context) }, enabled = false) {
                    Text(
                        stringResource(R.string.update_btn)
                    )
                }
            }
        }
    }
}