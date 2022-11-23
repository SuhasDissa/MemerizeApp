/*******************************************************************************
 Created By Suhas Dissanayake on 11/23/22, 4:16 PM
 Copyright (c) 2022
 https://github.com/SuhasDissa/
 All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object Home : Destination {
    override val route = "home"
}

object RedditMemeView : Destination {
    override val route = "memeview"
    val routeWithArgs = "$route/{category}"
    val arguments = listOf(navArgument("category") { type = NavType.StringType })
}

object TGMemeView : Destination {
    override val route = "tgmemeview"
    val routeWithArgs = "$route/{category}"
    val arguments = listOf(navArgument("category") { type = NavType.StringType })
}

object FunnyVideoView : Destination {
    override val route = "funvidview"
}

object FeedView : Destination {
    override val route = "feedview"
}

object Settings : Destination {
    override val route = "settings"
}

object About : Destination {
    override val route = "about"
}

object PhotoView : Destination {
    override val route = "memescreen"
    val routeWithArgs = "$route/{url}"
    val arguments = listOf(navArgument("url") { type = NavType.StringType })
}

object VideoPlayer : Destination {
    override val route = "video"
    val routeWithArgs = "$route/{url}"
    val arguments = listOf(navArgument("url") { type = NavType.StringType })
}

object TextViewer : Destination {
    override val route = "textviewer"
    val routeWithArgs = "$route/{text}"
    val arguments = listOf(navArgument("text") { type = NavType.StringType })
}