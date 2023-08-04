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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import app.suhasdissa.memerize.ui.components.SettingItem
import app.suhasdissa.memerize.utils.SaveDirectoryKey
import app.suhasdissa.memerize.utils.preferences

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
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
    LazyColumn(
        modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            SettingItem(
                title = "Download Location",
                description = "Select Meme download location",
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
                title = "About",
                description = "Developer Contact",
                onClick = { onAboutClick() },
                icon = Icons.Outlined.Info
            )
        }
    }
}
