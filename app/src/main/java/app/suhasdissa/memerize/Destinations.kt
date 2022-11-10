package app.suhasdissa.memerize

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object Home : Destination {
    override val route = "home"
}

object MemeView : Destination {
    override val route = "memeview"
}
object FunnyVideoView : Destination {
    override val route = "funvidview"
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
object WebVideoPlayer : Destination {
    override val route = "webvideo"
    val routeWithArgs = "$route/{url}"
    val arguments = listOf(
        navArgument("url") { type = NavType.StringType }
    )
}

object VideoPlayer : Destination {
    override val route = "video"
    val routeWithArgs = "$route/{url}"
    val arguments = listOf(
        navArgument("url") { type = NavType.StringType }
    )
}