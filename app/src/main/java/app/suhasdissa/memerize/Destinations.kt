package app.suhasdissa.memerize

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