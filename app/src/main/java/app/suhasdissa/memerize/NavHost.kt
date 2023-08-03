/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.suhasdissa.memerize.backend.viewmodels.PlayerViewModel
import app.suhasdissa.memerize.backend.viewmodels.RedditViewModel
import app.suhasdissa.memerize.ui.screens.home.CommunityScreen
import app.suhasdissa.memerize.ui.screens.home.HomeScreen
import app.suhasdissa.memerize.ui.screens.home.SubredditScreen
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
    val redditViewModel: RedditViewModel = viewModel(factory = RedditViewModel.Factory)
    val playerViewModel: PlayerViewModel = viewModel(factory = PlayerViewModel.Factory)
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
        modifier = modifier
    ) {
        composable(route = Destination.Home.route) {
            HomeScreen(
                onClickMemeView = { subreddit ->
                    navController.navigateTo("${Destination.RedditMemeView.route}/$subreddit")
                },
                redditViewModel = redditViewModel
            )
        }
        composable(route = Destination.Settings.route) {
            SettingsScreen(onAboutClick = {
                navController.navigateTo(Destination.About.route)
            })
        }
        composable(route = Destination.Subreddits.route) {
            SubredditScreen()
        }
        composable(route = Destination.Communities.route) {
            CommunityScreen()
        }
        composable(route = Destination.About.route) {
            AboutScreen()
        }
        composable(
            route = Destination.RedditMemeView.routeWithArgs,
            arguments = Destination.RedditMemeView.arguments
        ) {
            val subreddit = it.arguments?.getString("category")
            if (subreddit != null) {
                RedditMemeScreen(
                    redditViewModel = redditViewModel,
                    onClickMeme = { url ->
                        navController.navigateTo("${Destination.PhotoView.route}/$url")
                    },
                    onClickVideo = { url ->
                        navController.navigateTo("${Destination.VideoPlayer.route}/$url")
                    },
                    subreddit = subreddit
                )
            }
        }
        composable(
            route = Destination.PhotoView.routeWithArgs,
            arguments = Destination.PhotoView.arguments
        ) {
            val imgurl = it.arguments?.getString("url")
            if (imgurl != null) {
                PhotoView(imgurl)
            }
        }

        composable(
            route = Destination.VideoPlayer.routeWithArgs,
            arguments = Destination.VideoPlayer.arguments
        ) {
            val url = it.arguments?.getString("url")
            if (url != null) {
                VideoView(url = url, playerViewModel = playerViewModel)
            }
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}
