package app.suhasdissa.memerize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.suhasdissa.memerize.backend.ImageViewModel
import app.suhasdissa.memerize.ui.components.PhotoView
import app.suhasdissa.memerize.ui.components.WebViewer
import app.suhasdissa.memerize.ui.screens.*

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
            HomeScreen(
                onClickMemeView = {
                    navController.navigateTo(MemeView.route)
                }
            )
        }
        composable(route = Settings.route) {
            SettingsScreen()
        }
        composable(route = MemeView.route) {
            val memeViewModel: ImageViewModel = viewModel()
            MemeViewScreen(
                refresh = memeViewModel::getMemePhotos,
                memeUiState = memeViewModel.memeUiState,
                onClickMeme = { url ->
                    navController.navigateToMeme(url)
                },
                onClickVideo = { url ->
                    navController.navigateToVideo(url)
                }
            )
        }
        composable(
            route = OneMemeView.routeWithArgs,
            arguments = OneMemeView.arguments
        ) {
            val imgurl = it.arguments?.getString("url")
            if (imgurl != null) {
                PhotoView(imgurl)
            }
        }
        composable(
            route =WebVideoPlayer.routeWithArgs,
            arguments = WebVideoPlayer.arguments
        ) {
            val url = it.arguments?.getString("url")
            if (url != null) {
                WebViewer(url)
            }
        }
    }
}

fun NavHostController.navigateTo(route: String) =
    this.navigate(route) {
        /*popUpTo(
            this@navigateTo.graph.findStartDestination().id
        ) {
            saveState = true
        }*/
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateToMeme(url: String) {
    this.navigateTo("${OneMemeView.route}/$url")
}
private fun NavHostController.navigateToVideo(url: String) {
    this.navigateTo("${WebVideoPlayer.route}/$url")
}