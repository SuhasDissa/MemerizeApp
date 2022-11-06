package app.suhasdissa.memerize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
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
                    navController.navigateSingleTopTo(MemeView.route)
                },
                onClickMemeWebView = {
                    navController.navigateSingleTopTo(MemeWebView.route)
                }
            )
        }
        composable(route = MemeWebView.route) {
            MemeWebViewScreen()
        }

        composable(route = Settings.route) {
            SettingsScreen()
        }
        composable(route = MemeView.route) {
            val memeViewModel: ImageViewModel = viewModel()
            MemeViewScreen(
                memeUiState = memeViewModel.memeUiState
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        /* Pop up to the start destination of the graph to
         avoid building up a large stack of destinations
         on the back stack as users select items*/
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }