/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 6:10 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import app.suhasdissa.memerize.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MemerizeApp() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        AppNavHost(navController = navController, modifier = Modifier)
    }
}
