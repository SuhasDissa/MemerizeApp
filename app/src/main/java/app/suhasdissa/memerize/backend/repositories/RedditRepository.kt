/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import androidx.annotation.WorkerThread
import app.suhasdissa.memerize.backend.apis.RedditApi
import app.suhasdissa.memerize.backend.databases.RedditMeme
import app.suhasdissa.memerize.backend.databases.RedditMemeDao

interface RedditRepository {
    suspend fun getData(subreddit: String, time: String): ArrayList<Meme>
}

data class Meme(
    val url: String, val isVideo: Boolean, val preview: String
)

class NetworkRedditRepository(private val redditMemeDao: RedditMemeDao) : RedditRepository {
    override suspend fun getData(subreddit: String, time: String): ArrayList<Meme> {
        var memesList: ArrayList<Meme>
        try {
            memesList = getNetworkData(subreddit, time)
            Thread {
                insertMemes(memesList.map { RedditMeme(it.url, it.isVideo, it.preview) })
            }.start()
        } catch (e: Exception) {
            memesList = getLocalData()
        }
        return memesList
    }

    private suspend fun getNetworkData(subreddit: String, time: String): ArrayList<Meme> {
        val memeList: ArrayList<Meme> = arrayListOf()
        val redditData = RedditApi.retrofitService.getRedditData(subreddit, time).data.children
        redditData.forEach { child ->
            val url = child.Childdata.url
            if (url.contains("i.redd.it")) {
                memeList.add(Meme(url, false, ""))
            } else if (url.contains("v.redd.it")) {
                val dashUrl = child.Childdata.secure_media?.reddit_video?.dash_url
                val previewUrl = child.Childdata.preview?.images?.get(0)?.source?.url
                if (dashUrl != null && previewUrl != null) {
                    memeList.add(
                        Meme(
                            dashUrl, true, previewUrl.replace("&amp;", "&")
                        )
                    )
                }
            }
        }
        return memeList
    }

    private fun getLocalData(): ArrayList<Meme> {
        return redditMemeDao.getAll().mapTo(ArrayList()) {
            Meme(
                it.url, it.isVideo, it.preview
            )
        }
    }

    @WorkerThread
    private fun insertMemes(memes: List<RedditMeme>) {
        redditMemeDao.insertAll(memes)
    }

}