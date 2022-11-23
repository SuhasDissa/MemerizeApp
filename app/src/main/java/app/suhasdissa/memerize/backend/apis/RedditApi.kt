/*******************************************************************************
 Created By Suhas Dissanayake on 11/23/22, 4:16 PM
 Copyright (c) 2022
 https://github.com/SuhasDissa/
 All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend

import app.suhasdissa.memerize.backend.serializables.Reddit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private val json = Json { ignoreUnknownKeys = true }

@OptIn(ExperimentalSerializationApi::class)
private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.reddit.com/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface ApiService {
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
    @GET("r/{subreddit}/top.json?sort=top&limit=100")
    suspend fun getRedditData(
        @Path("subreddit") subreddit: String,
        @Query("t") time: String
    ): Reddit
}

object RedditApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}