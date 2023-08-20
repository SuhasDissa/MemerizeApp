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
    object RedditMemeView : Destination("reddit_memeview")
    object LemmyMemeView : Destination("lemmy_memeview")
    object Settings : Destination("settings")
    object Subreddits : Destination("subreddits")
    object Communities : Destination("communities")
    object About : Destination("about")
    object RedditFeed : Destination("reddit_feed") {
        val routeWithArgs = "$route/{id}"
        val arguments = listOf(navArgument("id") { type = NavType.IntType })
    }

    object LemmyFeed : Destination("lemmy_feed") {
        val routeWithArgs = "$route/{id}"
        val arguments = listOf(navArgument("id") { type = NavType.IntType })
    }
}
