package app.suhasdissa.memerize

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import app.suhasdissa.memerize.ui.components.PhotoView
import app.suhasdissa.memerize.ui.components.VideoView
import app.suhasdissa.memerize.ui.screens.*
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
            HomeScreen(onClickMemeView = {
                navController.navigateTo(MemeView.route)
            }, onClickFunnyVideo = {
                navController.navigateTo(FunnyVideoView.route)
            }, onClickFeed = {
                navController.navigateTo(FeedView.route)
            }, onClickTG = {
                navController.navigateTo(TGMemeView.route)
            })
        }
        composable(route = Settings.route,
            enterTransition = { scaleIn(initialScale = .9f, animationSpec = tween(200)) },
            exitTransition = { scaleOut(targetScale = .9f, animationSpec = tween(200)) }) {
            SettingsScreen()
        }
        composable(route = About.route,
            enterTransition = { scaleIn(initialScale = .9f, animationSpec = tween(200)) },
            exitTransition = { scaleOut(targetScale = .9f, animationSpec = tween(200)) }) {
            AboutScreen()
        }
        composable(route = MemeView.route,
            enterTransition = { scaleIn(initialScale = .9f, animationSpec = tween(200)) },
            exitTransition = { scaleOut(targetScale = .9f, animationSpec = tween(200)) }) {
            MemeViewScreen(onClickMeme = { url ->
                navController.navigateToPhotoViewer(url)
            }, onClickVideo = { url ->
                navController.navigateToVideoPlayer(url)
            })
        }
        composable(route = TGMemeView.route,
            enterTransition = { scaleIn(initialScale = .9f, animationSpec = tween(200)) },
            exitTransition = { scaleOut(targetScale = .9f, animationSpec = tween(200)) }) {
            TelegramMemeScreen(onClickMeme = { url ->
                navController.navigateToPhotoViewer(url)
            }, onClickVideo = { url ->
                navController.navigateToVideoPlayer(url)
            })
        }
        composable(route = FunnyVideoView.route,
            enterTransition = { scaleIn(initialScale = .9f, animationSpec = tween(200)) },
            exitTransition = { scaleOut(targetScale = .9f, animationSpec = tween(200)) }) {
            FunnyVideoScreen(onClickTextCard = { url ->
                navController.navigateToVideoPlayer(url)
            })
        }
        composable(route = FeedView.route,
            enterTransition = { scaleIn(initialScale = .9f, animationSpec = tween(200)) },
            exitTransition = { scaleOut(targetScale = .9f, animationSpec = tween(200)) }) {
            FeedScreen()
        }
        composable(route = PhotoView.routeWithArgs,
            arguments = PhotoView.arguments,
            enterTransition = { scaleIn(initialScale = .9f, animationSpec = tween(200)) },
            exitTransition = { scaleOut(targetScale = .9f, animationSpec = tween(200)) }) {

            val imgurl = it.arguments?.getString("url")
            if (imgurl != null) {
                PhotoView(imgurl)
            }
        }

        composable(route = VideoPlayer.routeWithArgs,
            arguments = VideoPlayer.arguments,
            enterTransition = { scaleIn(initialScale = .9f, animationSpec = tween(200)) },
            exitTransition = { scaleOut(targetScale = .9f, animationSpec = tween(200)) }) {
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

private fun NavHostController.navigateToPhotoViewer(url: String) {
    this.navigateTo("${PhotoView.route}/$url")
}

private fun NavHostController.navigateToVideoPlayer(url: String) {
    this.navigateTo("${VideoPlayer.route}/$url")
}