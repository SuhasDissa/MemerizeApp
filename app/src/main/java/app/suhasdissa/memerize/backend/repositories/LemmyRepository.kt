/*******************************************************************************
Created By Suhas Dissanayake on 8/3/23, 6:55 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import app.suhasdissa.memerize.backend.apis.LemmyApi
import app.suhasdissa.memerize.backend.database.dao.CommunityDAO
import app.suhasdissa.memerize.backend.database.dao.LemmyMemeDAO
import app.suhasdissa.memerize.backend.database.entity.LemmyCommunity
import app.suhasdissa.memerize.backend.database.entity.LemmyMeme
import kotlinx.coroutines.flow.Flow

interface LemmyRepository {
    suspend fun getOnlineData(community: LemmyCommunity, time: String): List<LemmyMeme>?
    suspend fun getLocalData(community: LemmyCommunity): List<LemmyMeme>
    fun getCommunities(): Flow<List<LemmyCommunity>>
    suspend fun getCommunityInfo(name: String, instance: String): LemmyCommunity?
    suspend fun insertCommunity(community: LemmyCommunity)
    suspend fun removeCommunity(community: LemmyCommunity)
}

class LemmyRepositoryImpl(
    private val communityDAO: CommunityDAO,
    private val lemmyDAO: LemmyMemeDAO,
    private val lemmyApi: LemmyApi
) :
    LemmyRepository {

    override suspend fun getOnlineData(
        community: LemmyCommunity,
        time: String
    ): List<LemmyMeme>? {
        return try {
            val memesList = getNetworkData(community, time)
            Thread {
                insertMemes(memesList)
            }.start()
            memesList
        } catch (e: Exception) {
            Log.e("Lemmy Repository", e.toString())
            null
        }
    }

    private suspend fun getNetworkData(
        community: LemmyCommunity,
        time: String
    ): List<LemmyMeme> {
        val memeList: ArrayList<LemmyMeme> = arrayListOf()
        val lemmyData = lemmyApi.getLemmyData(
            instance = community.instance,
            community = community.community,
            sort = time
        ).posts
        lemmyData.forEach { post ->
            val url = post.post?.url ?: ""
            val title = post.post?.name ?: ""
            if (url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("png")) {
                val id = url.hashCode().toString()
                memeList.add(
                    LemmyMeme(
                        id,
                        url,
                        title,
                        false,
                        url,
                        community.name,
                        community.instance
                    )
                )
            }
        }
        return memeList
    }

    override suspend fun getLocalData(community: LemmyCommunity): List<LemmyMeme> =
        lemmyDAO.getAll(community.community, community.instance)

    override fun getCommunities(): Flow<List<LemmyCommunity>> = communityDAO.getAll()

    override suspend fun getCommunityInfo(name: String, instance: String): LemmyCommunity? {
        return try {
            val community =
                lemmyApi.getCommunity(instance, name).communityView?.community ?: return null
            return LemmyCommunity(name, instance, community.icon, community.name ?: name)
        } catch (e: Exception) {
            Log.e("Lemmy Repository", e.toString())
            null
        }
    }

    override suspend fun insertCommunity(community: LemmyCommunity) = communityDAO.insert(community)

    override suspend fun removeCommunity(community: LemmyCommunity) = communityDAO.delete(community)

    @WorkerThread
    private fun insertMemes(memes: List<LemmyMeme>) {
        lemmyDAO.insertAll(memes)
    }
}
