/*******************************************************************************
Created By Suhas Dissanayake on 8/3/23, 4:40 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.apis

import app.suhasdissa.memerize.backend.model.LemmyAbout
import app.suhasdissa.memerize.backend.model.LemmyResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val header =
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36" // ktlint-disable max-line-length

interface LemmyApi {
    /**
     * @param community The name of the community.
     * @param sort "Active", "Hot", "MostComments", "New", "NewComments",
     *                 "Old", "TopAll", "TopDay", "TopMonth", "TopWeek",
     *                 "TopYear".
     */
    @Headers(header)
    @GET("https://{instance}/api/v3/post/list")
    suspend fun getLemmyData(
        @Path("instance") instance: String,
        @Query("community_name") community: String,
        @Query("sort") sort: String
    ): LemmyResponse

    @Headers(header)
    @GET("https://{instance}/api/v3/community")
    suspend fun getCommunity(
        @Path("instance") instance: String,
        @Query("name") name: String
    ): LemmyAbout
}
