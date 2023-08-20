package app.suhasdissa.memerize.backend.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import app.suhasdissa.memerize.backend.apis.RedditApi
import app.suhasdissa.memerize.backend.database.dao.RedditMemeDao
import app.suhasdissa.memerize.backend.database.entity.RedditCommunity
import app.suhasdissa.memerize.backend.database.entity.RedditMeme
import app.suhasdissa.memerize.backend.model.Sort

interface RedditMemeRepository : MemeRepository<RedditMeme, RedditCommunity>

class RedditMemeRepositoryImpl(
    private val redditMemeDao: RedditMemeDao,
    private val redditApi: RedditApi
) : RedditMemeRepository {

    private val imageRegex = Regex("^.+\\.(jpg|jpeg|png|webp)\$")
    override suspend fun getOnlineData(
        community: RedditCommunity,
        sort: Sort
    ): List<RedditMeme>? {
        val srt = when (sort) {
            is Sort.Top -> sort.redditSort to sort.redditT
            else -> sort.redditSort to null
        }
        return try {
            val memesList = getNetworkData(community.id, srt.first, srt.second)
            Thread {
                insertMemes(memesList)
            }.start()
            memesList
        } catch (e: Exception) {
            Log.e("Reddit Repository", e.message, e)
            null
        }
    }

    override suspend fun getLocalData(community: RedditCommunity): List<RedditMeme> =
        redditMemeDao.getAll(community.id)

    private suspend fun getNetworkData(
        subreddit: String,
        sort: String,
        time: String?
    ): List<RedditMeme> {
        val memeList: ArrayList<RedditMeme> = arrayListOf()
        val redditData = redditApi.getRedditData(subreddit, sort, time).data.children
        redditData.forEach { child ->
            val url = child.Childdata.url
            if (url.matches(imageRegex)) {
                val id = url.hashCode().toString()
                memeList.add(
                    RedditMeme(
                        id,
                        url,
                        child.Childdata.title,
                        false,
                        "",
                        subreddit,
                        child.Childdata.permalink?.let { "https://www.reddit.com$it" }
                    )
                )
            } else if (url.contains("v.redd.it") || child.Childdata.preview?.redditVideo?.dash_url != null) {
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
                            subreddit,
                            child.Childdata.permalink?.let { "https://www.reddit.com$it" }
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
