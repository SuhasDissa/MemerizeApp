package app.suhasdissa.memerize

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object Home : Destination {
    override val route = "home"
}

object MemeWebView : Destination {
    override val route = "memewebview"
}

object MemeView : Destination {
    override val route = "memeview"
}

object Settings : Destination {
    override val route = "settings"
}

object OneMemeView : Destination {
    override val route = "memescreen"
    val routeWithArgs = "$route/{url}"
    val arguments = listOf(
        navArgument("url") { type = NavType.StringType }
    )
}