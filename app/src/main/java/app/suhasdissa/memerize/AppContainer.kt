/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:00 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import app.suhasdissa.memerize.backend.databases.MemeDatabase
import app.suhasdissa.memerize.backend.repositories.NetworkRedditRepository
import app.suhasdissa.memerize.backend.repositories.RedditRepository

interface AppContainer {
    val redditRepository: RedditRepository
}

class DefaultAppContainer(database: MemeDatabase) : AppContainer {
    override val redditRepository: RedditRepository by lazy {
        NetworkRedditRepository(database.redditMemeDao())
    }
}
