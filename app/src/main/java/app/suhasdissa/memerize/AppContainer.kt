/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:00 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import android.content.Context
import app.suhasdissa.memerize.backend.RedditAuth
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
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

interface AppContainer {
    val redditApi: RedditApi
    val lemmyApi: LemmyApi
    val redditMemeRepository: RedditMemeRepository
    val lemmyMemeRepository: LemmyMemeRepository
    val lemmyCommunityRepository: LemmyCommunityRepository
    val redditCommunityRepository: RedditCommunityRepository
}

private class RedditCookieInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val cookie = RedditAuth.getCookie()
        val request = if (cookie != null) {
            chain.request().newBuilder()
                .header("Cookie", cookie)
                .build()
        } else {
            chain.request()
        }
        val response = chain.proceed(request)
        if (response.code == 401 || response.code == 403) {
            RedditAuth.clearAuth(context)
        }
        return response
    }
}

class DefaultAppContainer(database: MemeDatabase, context: Context) : AppContainer {
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

    private val redditOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(RedditCookieInterceptor(context))
        .build()

    private val redditRetrofit = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/")
        .client(redditOkHttpClient)
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
