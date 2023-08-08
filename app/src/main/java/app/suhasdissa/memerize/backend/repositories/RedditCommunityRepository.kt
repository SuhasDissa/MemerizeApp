/*******************************************************************************
Created By Suhas Dissanayake on 8/4/23, 10:15 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import app.suhasdissa.memerize.backend.apis.RedditApi
import app.suhasdissa.memerize.backend.database.dao.SubredditDAO
import app.suhasdissa.memerize.backend.database.entity.RedditCommunity
import kotlinx.coroutines.flow.Flow

interface RedditCommunityRepository : CommunityRepository<RedditCommunity>
class RedditCommunityRepositoryImpl(
    private val subredditDAO: SubredditDAO,
    private val redditApi: RedditApi
) : RedditCommunityRepository {

    override fun getCommunities(): Flow<List<RedditCommunity>> = subredditDAO.getAll()

    override suspend fun getCommunityInfo(community: RedditCommunity): RedditCommunity? {
        return try {
            val info = redditApi.getAboutSubreddit(community.id).data ?: return null
            community.copy(iconUrl = info.communityIconUrl, name = info.displayName ?: community.id)
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun insertCommunity(community: RedditCommunity) =
        subredditDAO.insert(community)

    override suspend fun removeCommunity(community: RedditCommunity) =
        subredditDAO.delete(community)
}
