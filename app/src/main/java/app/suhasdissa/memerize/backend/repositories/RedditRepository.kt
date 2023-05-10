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
    suspend fun getOnlineData(subreddit: String, time: String): ArrayList<Meme>?
    suspend fun getLocalData(subreddit: String): ArrayList<Meme>
}

data class Meme(
    val url: String,
    val title: String,
    val isVideo: Boolean,
    val preview: String,
    val category: String
)

class NetworkRedditRepository(private val redditMemeDao: RedditMemeDao) : RedditRepository {
    override suspend fun getOnlineData(subreddit: String, time: String): ArrayList<Meme>? {
        try {
            val memesList = getNetworkData(subreddit, time)
            Thread {
                insertMemes(memesList.filter{ !it.isVideo }.map{
                    RedditMeme(
                        it.url,
                        it.title,
                        it.isVideo,
                        it.preview,
                        subreddit = it.category
                    )
                })
            }.start()
            return memesList
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun getLocalData(subreddit: String): ArrayList<Meme> {
        return redditMemeDao.getAll(subreddit).mapTo(ArrayList()) {
            Meme(
                it.url, it.title, it.isVideo, it.preview, it.subreddit
            )
        }
    }

    private suspend fun getNetworkData(subreddit: String, time: String): ArrayList<Meme> {
        val memeList: ArrayList<Meme> = arrayListOf()
        val redditData = RedditApi.retrofitService.getRedditData(subreddit, time).data.children
        redditData.forEach { child ->
            val url = child.Childdata.url
            if (url.contains("i.redd.it")) {
                memeList.add(Meme(url, child.Childdata.title, false, "", subreddit))
            } else if (url.contains("v.redd.it")) {
                val dashUrl = child.Childdata.secure_media?.reddit_video?.dash_url
                val previewUrl = child.Childdata.preview?.images?.get(0)?.source?.url
                if (dashUrl != null && previewUrl != null) {
                    memeList.add(
                        Meme(
                            dashUrl,
                            child.Childdata.title,
                            true,
                            previewUrl.replace("&amp;", "&"),
                            subreddit
                        )
                    )
                }
            }
        }
        return memeList
    }


    @WorkerThread
    private fun insertMemes(memes: List<RedditMeme>) {
        redditMemeDao.insertAll(memes)
    }

}