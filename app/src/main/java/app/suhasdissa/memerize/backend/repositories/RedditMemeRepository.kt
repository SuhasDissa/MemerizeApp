/*******************************************************************************
Created By Suhas Dissanayake on 8/4/23, 9:06 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import androidx.annotation.WorkerThread
import app.suhasdissa.memerize.backend.apis.RedditApi
import app.suhasdissa.memerize.backend.database.dao.RedditMemeDao
import app.suhasdissa.memerize.backend.database.entity.RedditCommunity
import app.suhasdissa.memerize.backend.database.entity.RedditMeme

interface RedditMemeRepository : MemeRepository<RedditMeme, RedditCommunity>

class RedditMemeRepositoryImpl(
    private val redditMemeDao: RedditMemeDao,
    private val redditApi: RedditApi
) : RedditMemeRepository {

    private val imageRegex = Regex("^.+\\.(jpg|jpeg|png|webp)\$")
    private val gifvRegex = Regex("^.+\\.gifv\$")
    override suspend fun getOnlineData(
        community: RedditCommunity,
        time: String
    ): List<RedditMeme>? {
        return try {
            val memesList = getNetworkData(community.id, time)
            Thread {
                insertMemes(memesList)
            }.start()
            memesList
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getLocalData(community: RedditCommunity): List<RedditMeme> =
        redditMemeDao.getAll(community.id)

    private suspend fun getNetworkData(subreddit: String, time: String): List<RedditMeme> {
        val memeList: ArrayList<RedditMeme> = arrayListOf()
        val redditData = redditApi.getRedditData(subreddit, time).data.children
        redditData.forEach { child ->
            val url = child.Childdata.url
            if (url.matches(imageRegex)) {
                val id = url.hashCode().toString()
                memeList.add(RedditMeme(id, url, child.Childdata.title, false, "", subreddit))
            } else if (url.contains("v.redd.it") || url.matches(gifvRegex)) {
                val dashUrl = child.Childdata.secure_media?.reddit_video?.dash_url
                    ?: child.Childdata.preview?.redditVideo?.dash_url
                val previewUrl = child.Childdata.preview?.images?.get(0)?.source?.url
                if (dashUrl != null && previewUrl != null) {
                    val id = url.hashCode().toString()
                    memeList.add(
                        RedditMeme(
                            id,
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
