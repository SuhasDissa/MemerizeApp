/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.apis

import app.suhasdissa.memerize.backend.model.Reddit
import app.suhasdissa.memerize.backend.model.RedditAboutResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val header =
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36" // ktlint-disable max-line-length

interface RedditApi {
    @Headers(header)
    @GET("r/{subreddit}/{sort}.json")
    suspend fun getRedditData(
        @Path("subreddit") subreddit: String,
        @Path("sort") sort: String,
        @Query("t") time: String? = null
    ): Reddit

    @Headers(header)
    @GET("r/{subreddit}/about.json")
    suspend fun getAboutSubreddit(
        @Path("subreddit") subreddit: String
    ): RedditAboutResponse
}
