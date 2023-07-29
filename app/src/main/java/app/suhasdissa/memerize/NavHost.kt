/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.suhasdissa.memerize.ui.screens.primary.HomeScreen
import app.suhasdissa.memerize.ui.screens.primary.RedditMemeScreen
import app.suhasdissa.memerize.ui.screens.secondary.PhotoView
import app.suhasdissa.memerize.ui.screens.secondary.VideoView
import app.suhasdissa.memerize.ui.screens.settings.AboutScreen
import app.suhasdissa.memerize.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(onClickMemeView = { subreddit ->
                navController.navigateTo("${RedditMemeView.route}/$subreddit")
            }, onClickSettings = {
                navController.navigateTo(Settings.route)
            })
        }
        composable(route = Settings.route) {
            SettingsScreen(onAboutClick = {
                navController.navigateTo(About.route)
            })
        }
        composable(route = About.route) {
            AboutScreen()
        }
        composable(route = RedditMemeView.routeWithArgs, arguments = RedditMemeView.arguments) {
            val subreddit = it.arguments?.getString("category")
            if (subreddit != null) {
                RedditMemeScreen(
                    onClickMeme = { url ->
                        navController.navigateTo("${PhotoView.route}/$url")
                    },
                    onClickVideo = {
                        navController.navigateTo(VideoPlayer.route)
                    },
                    subreddit = subreddit
                )
            }
        }
        composable(
            route = PhotoView.routeWithArgs,
            arguments = PhotoView.arguments
        ) {
            val imgurl = it.arguments?.getString("url")
            if (imgurl != null) {
                PhotoView(imgurl)
            }
        }

        composable(
            route = VideoPlayer.route
        ) {
            VideoView()
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}
