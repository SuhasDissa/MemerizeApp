package app.suhasdissa.memerize

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.navigation.NavHostController
import app.suhasdissa.memerize.ui.components.PhotoView
import app.suhasdissa.memerize.ui.components.VideoView
import app.suhasdissa.memerize.ui.components.WebViewer
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
        composable(route = Settings.route, enterTransition = {
            scaleIn(
                transformOrigin = TransformOrigin(1f, 0f), animationSpec = tween(700)
            )
        }, exitTransition = {
            scaleOut(
                transformOrigin = TransformOrigin(1f, 0f), animationSpec = tween(700)
            )
        }) {
            SettingsScreen()
        }
        composable(route = About.route, enterTransition = {
            scaleIn(
                transformOrigin = TransformOrigin(1f, 0f), animationSpec = tween(700)
            )
        }, exitTransition = {
            scaleOut(
                transformOrigin = TransformOrigin(1f, 0f), animationSpec = tween(700)
            )
        }) {
            AboutScreen()
        }
        composable(route = MemeView.route, enterTransition = {
            when (initialState.destination.route) {
                Home.route -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700)
                )
                else -> null
            }
        }, exitTransition = {
            when (targetState.destination.route) {
                Home.route -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700)
                )
                else -> null
            }

        }) {
            MemeViewScreen(onClickMeme = { url ->
                navController.navigateToMeme(url)
            }, onClickVideo = { url ->
                navController.navigateToVideo(url)
            })
        }
        composable(route = TGMemeView.route, enterTransition = {
            when (initialState.destination.route) {
                Home.route -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700)
                )
                else -> null
            }
        }, exitTransition = {
            when (targetState.destination.route) {
                Home.route -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700)
                )
                else -> null
            }

        }) {
            TelegramMemeScreen(onClickMeme = { url ->
                navController.navigateToMeme(url)
            }, onClickVideo = { url ->
                navController.navigateToVideo(url)
            })
        }
        composable(route = FunnyVideoView.route, enterTransition = {
            when (initialState.destination.route) {
                Home.route -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700)
                )
                else -> null
            }
        }, exitTransition = {
            when (targetState.destination.route) {
                Home.route -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700)
                )
                else -> null
            }

        }) {
            FunnyVideoScreen(onClickTextCard = { url ->
                navController.navigateToVideoPlayer(url)
            })
        }
        composable(route = FeedView.route, enterTransition = {
            when (initialState.destination.route) {
                Home.route -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700)
                )
                else -> null
            }
        }, exitTransition = {
            when (targetState.destination.route) {
                Home.route -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700)
                )
                else -> null
            }

        }) {
            FeedScreen()
        }
        composable(route = OneMemeView.routeWithArgs,
            arguments = OneMemeView.arguments,
            enterTransition = { scaleIn(initialScale = .8f, animationSpec = tween(700)) },
            exitTransition = { scaleOut(targetScale = .8f, animationSpec = tween(700)) }) {

            val imgurl = it.arguments?.getString("url")
            if (imgurl != null) {
                PhotoView(imgurl)
            }
        }
        composable(route = WebVideoPlayer.routeWithArgs,
            arguments = WebVideoPlayer.arguments,
            enterTransition = { scaleIn(initialScale = .8f, animationSpec = tween(700)) },
            exitTransition = { scaleOut(targetScale = .8f, animationSpec = tween(700)) }) {
            val url = it.arguments?.getString("url")
            if (url != null) {
                WebViewer(url)
            }
        }
        composable(route = VideoPlayer.routeWithArgs,
            arguments = VideoPlayer.arguments,
            enterTransition = { scaleIn(initialScale = .8f, animationSpec = tween(700)) },
            exitTransition = { scaleOut(targetScale = .8f, animationSpec = tween(700)) }) {
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

private fun NavHostController.navigateToMeme(url: String) {
    this.navigateTo("${OneMemeView.route}/$url")
}

private fun NavHostController.navigateToVideo(url: String) {
    this.navigateTo("${WebVideoPlayer.route}/$url")
}

private fun NavHostController.navigateToVideoPlayer(url: String) {
    this.navigateTo("${VideoPlayer.route}/$url")
}