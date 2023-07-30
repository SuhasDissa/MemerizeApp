/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.apis

import app.suhasdissa.memerize.backend.model.Reddit
import app.suhasdissa.memerize.backend.model.RedditAboutResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.reddit.com/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

private const val header =
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36"

interface ApiService {
    @Headers(header)
    @GET("r/{subreddit}/top.json?sort=top&limit=100")
    suspend fun getRedditData(
        @Path("subreddit") subreddit: String,
        @Query("t") time: String
    ): Reddit

    @Headers(header)
    @GET("r/{subreddit}/about.json")
    suspend fun getAboutSubreddit(
        @Path("subreddit") subreddit: String
    ): RedditAboutResponse
}

object RedditApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
