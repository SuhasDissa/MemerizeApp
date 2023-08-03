/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destination(val route: String) {
    object Home : Destination("home")
    object RedditMemeView : Destination("memeview") {
        val routeWithArgs = "$route/{category}"
        val arguments = listOf(navArgument("category") { type = NavType.StringType })
    }

    object Settings : Destination("settings")
    object Subreddits : Destination("subreddits")
    object Communities : Destination("communities")
    object About : Destination("about")
    object PhotoView : Destination("memescreen") {
        val routeWithArgs = "$route/{url}"
        val arguments = listOf(navArgument("url") { type = NavType.StringType })
    }

    object VideoPlayer : Destination("videoplayer") {
        val routeWithArgs = "$route/{url}"
        val arguments = listOf(navArgument("url") { type = NavType.StringType })
    }
}
