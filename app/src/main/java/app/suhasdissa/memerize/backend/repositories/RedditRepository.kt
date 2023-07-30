/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import androidx.annotation.WorkerThread
import app.suhasdissa.memerize.backend.apis.RedditApi
import app.suhasdissa.memerize.backend.database.dao.RedditMemeDao
import app.suhasdissa.memerize.backend.database.dao.SubredditDAO
import app.suhasdissa.memerize.backend.database.entity.RedditMeme
import app.suhasdissa.memerize.backend.database.entity.Subreddit
import app.suhasdissa.memerize.backend.model.RedditAboutResponse
import kotlinx.coroutines.flow.Flow

interface RedditRepository {
    suspend fun getOnlineData(subreddit: String, time: String): List<RedditMeme>?
    suspend fun getLocalData(subreddit: String): List<RedditMeme>
    fun getSubreddits(): Flow<List<Subreddit>>
    suspend fun getSubredditInfo(subreddit: String): RedditAboutResponse?
    suspend fun insertSubreddit(subreddit: Subreddit)
    suspend fun removeSubreddit(subreddit: Subreddit)
}

class RedditRepositoryImpl(
    private val redditMemeDao: RedditMemeDao,
    private val subredditDAO: SubredditDAO
) : RedditRepository {
    override suspend fun getOnlineData(subreddit: String, time: String): List<RedditMeme>? {
        return try {
            val memesList = getNetworkData(subreddit, time)
            Thread {
                insertMemes(memesList)
            }.start()
            memesList
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun insertSubreddit(subreddit: Subreddit) = subredditDAO.insert(subreddit)
    override suspend fun removeSubreddit(subreddit: Subreddit) = subredditDAO.delete(subreddit)
    override fun getSubreddits(): Flow<List<Subreddit>> = subredditDAO.getAll()
    override suspend fun getSubredditInfo(subreddit: String): RedditAboutResponse? {
        return try {
            RedditApi.retrofitService.getAboutSubreddit(subreddit)
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getLocalData(subreddit: String): List<RedditMeme> =
        redditMemeDao.getAll(subreddit)

    private suspend fun getNetworkData(subreddit: String, time: String): List<RedditMeme> {
        val memeList: ArrayList<RedditMeme> = arrayListOf()
        val redditData = RedditApi.retrofitService.getRedditData(subreddit, time).data.children
        redditData.forEach { child ->
            val url = child.Childdata.url
            if (url.contains("i.redd.it")) {
                val id = url.hashCode().toString()
                memeList.add(RedditMeme(id, url, child.Childdata.title, false, "", subreddit))
            } else if (url.contains("v.redd.it")) {
                val dashUrl = child.Childdata.secure_media?.reddit_video?.dash_url
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
