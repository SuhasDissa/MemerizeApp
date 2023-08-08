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
import app.suhasdissa.memerize.backend.repositories.LemmyCommunityRepository
import app.suhasdissa.memerize.backend.repositories.LemmyCommunityRepositoryImpl
import app.suhasdissa.memerize.backend.repositories.LemmyMemeRepository
import app.suhasdissa.memerize.backend.repositories.LemmyMemeRepositoryImpl
import app.suhasdissa.memerize.backend.repositories.RedditCommunityRepository
import app.suhasdissa.memerize.backend.repositories.RedditCommunityRepositoryImpl
import app.suhasdissa.memerize.backend.repositories.RedditMemeRepository
import app.suhasdissa.memerize.backend.repositories.RedditMemeRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val redditApi: RedditApi
    val lemmyApi: LemmyApi
    val redditMemeRepository: RedditMemeRepository
    val lemmyMemeRepository: LemmyMemeRepository
    val lemmyCommunityRepository: LemmyCommunityRepository
    val redditCommunityRepository: RedditCommunityRepository
}

class DefaultAppContainer(database: MemeDatabase) : AppContainer {
    override val redditMemeRepository: RedditMemeRepository by lazy {
        RedditMemeRepositoryImpl(database.redditMemeDao(), redditApi)
    }
    override val lemmyMemeRepository: LemmyMemeRepository by lazy {
        LemmyMemeRepositoryImpl(database.lemmyMemeDao(), lemmyApi)
    }
    override val lemmyCommunityRepository: LemmyCommunityRepository by lazy {
        LemmyCommunityRepositoryImpl(database.communityDao(), lemmyApi)
    }
    override val redditCommunityRepository: RedditCommunityRepository by lazy {
        RedditCommunityRepositoryImpl(database.subredditDao(), redditApi)
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
