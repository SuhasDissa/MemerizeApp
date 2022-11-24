/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import app.suhasdissa.memerize.backend.RedditApi

interface RedditRepository {
    suspend fun getData(subreddit: String, time: String): ArrayList<Meme>
}

data class Meme(
    val isVideo: Boolean, val url: String, val preview: String
)

private var MemeList: ArrayList<Meme> = arrayListOf()

class DefaultRedditRepository : RedditRepository {
    override suspend fun getData(subreddit: String, time: String): ArrayList<Meme> {

        MemeList = arrayListOf()
        val redditData = RedditApi.retrofitService.getRedditData(subreddit, time).data.children
        redditData.forEach { child ->
            val url = child.Childdata.url
            if (url.contains("i.redd.it")) {
                MemeList.add(Meme(false, url, url))
            } else if (url.contains("v.redd.it")) {
                val dashUrl = child.Childdata.secure_media?.reddit_video?.dash_url
                val previewUrl = child.Childdata.preview?.images?.get(0)?.source?.url
                if (dashUrl != null && previewUrl != null) {
                    MemeList.add(
                        Meme(
                            true, dashUrl, previewUrl.replace("&amp;", "&")
                        )
                    )
                }
            }
        }
        return MemeList
    }
}