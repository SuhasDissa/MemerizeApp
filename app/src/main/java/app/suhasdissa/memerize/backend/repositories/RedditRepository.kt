/*******************************************************************************
 Created By Suhas Dissanayake on 11/23/22, 4:16 PM
 Copyright (c) 2022
 https://github.com/SuhasDissa/
 All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import app.suhasdissa.memerize.backend.RedditApi
import app.suhasdissa.memerize.backend.serializables.Children

interface RedditRepository {
    suspend fun getData(subreddit: String, time: String): ArrayList<Children>
}

class DefaultRedditRepository : RedditRepository {
    override suspend fun getData(subreddit: String, time: String): ArrayList<Children> {
        return RedditApi.retrofitService.getRedditData(subreddit, time).data.children
    }
}