/*******************************************************************************
Created By Suhas Dissanayake on 7/9/23, 3:39 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.viewmodels.CheckUpdateViewModel
import app.suhasdissa.memerize.ui.components.SettingItem
import app.suhasdissa.memerize.utils.openBrowser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    updateViewModel: CheckUpdateViewModel = viewModel()
) {
    val context = LocalContext.current
    val githubRepo = "https://github.com/SuhasDissa/MemerizeApp"

    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(stringResource(R.string.about_title)) })
    }) { innerPadding ->
        LazyColumn(
            modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            item {
                SettingItem(
                    title = stringResource(R.string.readme),
                    description = stringResource(R.string.check_repo_and_readme),
                    onClick = { openBrowser(context, githubRepo) },
                    icon = Icons.Default.Description
                )
            }
            item {
                SettingItem(
                    title = stringResource(R.string.latest_release),
                    description = "${updateViewModel.latestVersion}",
                    onClick = {
                        openBrowser(
                            context,
                            "$githubRepo/releases/latest"
                        )
                    },
                    icon = Icons.Default.NewReleases
                )
            }
            item {
                SettingItem(
                    title = stringResource(R.string.github_issue),
                    description = stringResource(R.string.github_issue_description),
                    onClick = {
                        openBrowser(
                            context,
                            "$githubRepo/issues"
                        )
                    },
                    icon = Icons.Default.ContactSupport
                )
            }
            item {
                SettingItem(
                    title = stringResource(R.string.current_version),
                    description = "${updateViewModel.currentVersion}",
                    onClick = {},
                    icon = Icons.Default.Info
                )
            }
        }
    }
}

@Composable
@Preview
fun AboutScreenPreview() {
    AboutScreen()
}
