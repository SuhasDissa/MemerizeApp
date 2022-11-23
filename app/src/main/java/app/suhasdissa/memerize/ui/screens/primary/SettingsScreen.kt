/*******************************************************************************
 Created By Suhas Dissanayake on 11/23/22, 4:16 PM
 Copyright (c) 2022
 https://github.com/SuhasDissa/
 All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.utils.applySettingString
import app.suhasdissa.memerize.utils.getSettingString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val context = LocalContext.current
        Text(
            text = stringResource(R.string.reddit_settings),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(R.string.subreddit_section),
            style = MaterialTheme.typography.headlineSmall
        )
        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var value by remember {
                mutableStateOf(
                    getSettingString(
                        context,
                        "subreddit",
                        "tkasylum"
                    )
                )
            }

            TextField(value = value, onValueChange = { value = it })
            Button(onClick = { applySettingString(context, "subreddit", value) }, enabled = false) {
                Text(stringResource(R.string.save_btn))
            }
        }
        Text(
            text = stringResource(R.string.meme_limit),
            style = MaterialTheme.typography.headlineSmall
        )
        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var value by remember {
                mutableStateOf(
                    getSettingString(
                        context,
                        "redditlimit",
                        "100"
                    )
                )
            }

            TextField(value = value, onValueChange = { value = it })
            Button(
                onClick = { applySettingString(context, "redditlimit", value) },
                enabled = false
            ) {
                Text(stringResource(R.string.save_btn))
            }
        }

        Text(
            text = stringResource(R.string.telegram_settings),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(R.string.telegram_channel_section),
            style = MaterialTheme.typography.headlineSmall
        )
        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var value by remember {
                mutableStateOf(
                    getSettingString(
                        context,
                        "tgchannel",
                        "chap_lin_sl"
                    )
                )
            }

            TextField(value = value, onValueChange = { value = it })
            Button(onClick = { applySettingString(context, "tgchannel", value) }, enabled = false) {
                Text(stringResource(R.string.save_btn))
            }
        }
        Text(
            text = stringResource(R.string.meme_limit),
            style = MaterialTheme.typography.headlineSmall
        )
        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var value by remember { mutableStateOf(getSettingString(context, "tglimit", "20")) }

            TextField(value = value, onValueChange = { value = it })
            Button(onClick = { applySettingString(context, "tglimit", value) }, enabled = false) {
                Text(stringResource(R.string.save_btn))
            }
        }
    }
}