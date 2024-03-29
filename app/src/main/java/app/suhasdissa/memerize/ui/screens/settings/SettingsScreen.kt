/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.settings

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.ui.components.CacheSizeDialog
import app.suhasdissa.memerize.ui.components.SettingItem
import app.suhasdissa.memerize.utils.SaveDirectoryKey
import app.suhasdissa.memerize.utils.preferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onDrawerOpen: () -> Unit,
    onAboutClick: () -> Unit
) {
    val context = LocalContext.current
    val directoryPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            it ?: return@rememberLauncherForActivityResult
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            Log.d("FIle path", it.toString())
            context.preferences.edit { putString(SaveDirectoryKey, it.toString()) }
        }
    var showImageCacheDialog by remember { mutableStateOf(false) }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(navigationIcon = {
            IconButton(onClick = {
                onDrawerOpen.invoke()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.open_navigation_drawer)
                )
            }
        }, title = {
            Text(
                stringResource(R.string.settings),
                color = MaterialTheme.colorScheme.primary
            )
        })
    }) { paddingValues ->
        LazyColumn(
            Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SettingItem(
                    title = stringResource(R.string.download_location),
                    description = stringResource(R.string.select_meme_download_location),
                    onClick = {
                        val lastDir = context.preferences.getString(SaveDirectoryKey, null)
                            .takeIf { !it.isNullOrBlank() }
                        directoryPicker.launch(lastDir?.let { Uri.parse(it) })
                    },
                    icon = Icons.Outlined.Folder
                )
            }
            item {
                SettingItem(
                    title = stringResource(R.string.image_cache_limit),
                    description = stringResource(R.string.set_image_cache_limit),
                    onClick = {
                        showImageCacheDialog = true
                    },
                    icon = Icons.Default.Storage
                )
            }
            item {
                SettingItem(
                    title = stringResource(R.string.about),
                    description = stringResource(R.string.developer_contact),
                    onClick = { onAboutClick() },
                    icon = Icons.Outlined.Info
                )
            }
        }
    }

    if (showImageCacheDialog) {
        CacheSizeDialog {
            showImageCacheDialog = false
        }
    }
}
