/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:00 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import app.suhasdissa.memerize.backend.apis.LemmyApi
import app.suhasdissa.memerize.backend.apis.RedditApi
import app.suhasdissa.memerize.backend.database.MemeDatabase
import app.suhasdissa.memerize.backend.repositories.LemmyRepository
import app.suhasdissa.memerize.backend.repositories.LemmyRepositoryImpl
import app.suhasdissa.memerize.backend.repositories.RedditRepository
import app.suhasdissa.memerize.backend.repositories.RedditRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val redditApi: RedditApi
    val lemmyApi: LemmyApi
    val redditRepository: RedditRepository
    val lemmyRepository: LemmyRepository
}

class DefaultAppContainer(database: MemeDatabase) : AppContainer {
    override val redditRepository: RedditRepository by lazy {
        RedditRepositoryImpl(database.redditMemeDao(), database.subredditDao(), redditApi)
    }

    override val lemmyRepository: LemmyRepository by lazy {
        LemmyRepositoryImpl(database.communityDao(), lemmyApi)
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val redditRetrofit = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val lemmyRetrofit = Retrofit.Builder()
        .baseUrl("https://lemmy.ml/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    override val redditApi: RedditApi by lazy {
        redditRetrofit.create(RedditApi::class.java)
    }

    override val lemmyApi: LemmyApi by lazy {
        lemmyRetrofit.create(LemmyApi::class.java)
    }
}
