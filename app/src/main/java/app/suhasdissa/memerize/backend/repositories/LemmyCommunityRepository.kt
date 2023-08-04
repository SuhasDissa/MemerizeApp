/*******************************************************************************
Created By Suhas Dissanayake on 8/4/23, 10:15 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import android.util.Log
import app.suhasdissa.memerize.backend.apis.LemmyApi
import app.suhasdissa.memerize.backend.database.dao.CommunityDAO
import app.suhasdissa.memerize.backend.database.entity.LemmyCommunity
import kotlinx.coroutines.flow.Flow

class LemmyCommunityRepository(
    private val communityDAO: CommunityDAO,
    private val lemmyApi: LemmyApi
) : CommunityRepository<LemmyCommunity> {

    override fun getCommunities(): Flow<List<LemmyCommunity>> = communityDAO.getAll()

    override suspend fun getCommunityInfo(community: LemmyCommunity): LemmyCommunity? {
        return try {
            val comm =
                lemmyApi.getCommunity(community.instance, community.id).communityView?.community
                    ?: return null

            return community.copy(name = comm.name ?: community.id, iconUrl = comm.icon)
        } catch (e: Exception) {
            Log.e("Lemmy Repository", e.toString())
            null
        }
    }

    override suspend fun insertCommunity(community: LemmyCommunity) = communityDAO.insert(community)

    override suspend fun removeCommunity(community: LemmyCommunity) = communityDAO.delete(community)
}
