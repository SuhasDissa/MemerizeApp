/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import app.suhasdissa.memerize.ui.components.PhotoView
import app.suhasdissa.memerize.ui.screens.*
import app.suhasdissa.memerize.ui.screens.primary.*
import app.suhasdissa.memerize.ui.screens.secondary.TextView
import app.suhasdissa.memerize.ui.screens.secondary.VideoView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    AnimatedNavHost(
        navController = navController, startDestination = Home.route, modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(onClickMemeView = { subreddit ->
                navController.navigateTo("${RedditMemeView.route}/$subreddit")
            }, onClickFunnyVideo = {
                navController.navigateTo(FunnyVideoView.route)
            }, onClickFeed = {
                navController.navigateTo(FeedView.route)
            }, onClickTG = { channel ->
                navController.navigateTo("${TGMemeView.route}/$channel")
            })
        }
        composable(route = Settings.route) {
            SettingsScreen()
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
                    onClickVideo = { url ->
                        navController.navigateTo("${VideoPlayer.route}/$url")
                    },
                    subreddit = subreddit
                )
            }
        }
        composable(route = TGMemeView.routeWithArgs, arguments = TGMemeView.arguments) {
            val channel = it.arguments?.getString("category")
            if (channel != null) {
                TelegramMemeScreen(onClickMeme = { url ->
                    navController.navigateTo("${PhotoView.route}/$url")
                }, onClickVideo = { url ->
                    navController.navigateTo("${VideoPlayer.route}/$url")
                }, channel = channel)
            }
        }
        composable(route = FunnyVideoView.route) {
            FunnyVideoScreen(onClickTextCard = { url ->
                navController.navigateTo("${VideoPlayer.route}/$url")
            })
        }
        composable(route = FeedView.route) {
            FeedScreen(onClickTextCard = { text ->
                navController.navigateTo("${TextViewer.route}/$text")
            })
        }
        composable(
            route = PhotoView.routeWithArgs, arguments = PhotoView.arguments
        ) {
            val imgurl = it.arguments?.getString("url")
            if (imgurl != null) {
                PhotoView(imgurl)
            }

        }

        composable(
            route = VideoPlayer.routeWithArgs, arguments = VideoPlayer.arguments
        ) {
            val url = it.arguments?.getString("url")
            if (url != null) {
                VideoView(url)
            }
        }
        composable(
            route = TextViewer.routeWithArgs, arguments = TextViewer.arguments
        ) {
            val text = it.arguments?.getString("text")
            if (text != null) {
                TextView(text)
            }
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}