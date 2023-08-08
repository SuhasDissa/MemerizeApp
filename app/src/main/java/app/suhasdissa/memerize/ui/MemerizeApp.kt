/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 6:10 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui

import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.navigation.compose.rememberNavController
import app.suhasdissa.memerize.AppNavHost
import app.suhasdissa.memerize.Destination
import app.suhasdissa.memerize.navigateTo
import app.suhasdissa.memerize.ui.components.NavDrawerContent
import kotlinx.coroutines.launch

@Composable
fun MemerizeApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentDestination by remember {
        mutableStateOf<Destination>(Destination.Home)
    }
    val view = LocalView.current
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            NavDrawerContent(currentDestination = currentDestination, onDestinationSelected = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigateTo(it.route)
                currentDestination = it
            })
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            AppNavHost(
                navController = navController,
                onDrawerOpen = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    scope.launch {
                        drawerState.open()
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
