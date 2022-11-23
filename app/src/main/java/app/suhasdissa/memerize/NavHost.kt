package app.suhasdissa.memerize

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import app.suhasdissa.memerize.ui.components.PhotoView
import app.suhasdissa.memerize.ui.components.VideoView
import app.suhasdissa.memerize.ui.screens.*
import app.suhasdissa.memerize.ui.screens.primary.HomeScreen
import app.suhasdissa.memerize.ui.screens.primary.MemeViewScreen
import app.suhasdissa.memerize.ui.screens.primary.TelegramMemeScreen
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
            HomeScreen(onClickMemeView = { subreddit->
                navController.navigateTo("${MemeView.route}/$subreddit")
            }, onClickFunnyVideo = {
                navController.navigateTo(FunnyVideoView.route)
            }, onClickFeed = {
                navController.navigateTo(FeedView.route)
            }, onClickTG = {channel->
                navController.navigateTo("${TGMemeView.route}/$channel")
            })
        }
        composable(route = Settings.route) {
            SettingsScreen()
        }
        composable(route = About.route) {
            AboutScreen()
        }
        composable(route = MemeView.routeWithArgs, arguments = MemeView.arguments) {
            val subreddit = it.arguments?.getString("category")
            if (subreddit != null) {
                MemeViewScreen(onClickMeme = { url ->
                    navController.navigateTo("${PhotoView.route}/$url")
                }, onClickVideo = { url ->
                    navController.navigateTo("${VideoPlayer.route}/$url")
                },subreddit=subreddit)
            }
        }
        composable(route = TGMemeView.routeWithArgs, arguments = TGMemeView.arguments) {
            val channel = it.arguments?.getString("category")
            if (channel != null) {
                TelegramMemeScreen(onClickMeme = { url ->
                    navController.navigateTo("${PhotoView.route}/$url")
                }, onClickVideo = { url ->
                    navController.navigateTo("${VideoPlayer.route}/$url")
                },channel=channel)
            }
        }
        composable(route = FunnyVideoView.route) {
            FunnyVideoScreen(onClickTextCard = { url ->
                navController.navigateTo("${VideoPlayer.route}/$url")
            })
        }
        composable(route = FeedView.route) {
            FeedScreen()
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
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}