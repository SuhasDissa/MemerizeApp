/*******************************************************************************
Created By Suhas Dissanayake on 8/7/23, 6:41 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.apis

import app.suhasdissa.memerize.backend.model.redditvideo.MPD
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

private const val header =
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36" // ktlint-disable max-line-length

interface RedditVideoApi {
    @Headers(header)
    @GET
    suspend fun getRedditData(
        @Url url: String
    ): MPD
}
