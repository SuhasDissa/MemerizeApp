/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:00 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import app.suhasdissa.memerize.backend.repositories.NetworkRedditRepository
import app.suhasdissa.memerize.backend.repositories.NetworkTelegramRepository
import app.suhasdissa.memerize.backend.repositories.RedditRepository
import app.suhasdissa.memerize.backend.repositories.TelegramRepository

interface AppContainer{
    val redditRepository:RedditRepository
    val telegramRepository:TelegramRepository
}
class DefaultAppContainer:AppContainer {
    override val redditRepository: RedditRepository by lazy { NetworkRedditRepository() }
    override val telegramRepository: TelegramRepository by lazy { NetworkTelegramRepository() }
}