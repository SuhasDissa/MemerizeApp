/*******************************************************************************
Created By Suhas Dissanayake on 8/9/23, 9:56 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.utils.defaultImageCacheSize
import app.suhasdissa.memerize.utils.imageCacheKey
import app.suhasdissa.memerize.utils.rememberPreference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CacheSizeDialog(onDismissRequest: () -> Unit) {
    val cacheSizes = listOf(16, 32, 64, 128, 256, 512, 1024, 2048)
    var prefSize by rememberPreference(key = imageCacheKey, defaultValue = defaultImageCacheSize)
    AlertDialog(
        onDismissRequest,
        title = { Text(stringResource(R.string.change_image_cache_size)) },
        confirmButton = {
            Button(onClick = {
                onDismissRequest.invoke()
            }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = cacheSizes) {
                    FilterChip(
                        selected = prefSize == it,
                        onClick = { prefSize = it },
                        label = {
                            Text("$it MB")
                        }
                    )
                }
            }
        }
    )
}
