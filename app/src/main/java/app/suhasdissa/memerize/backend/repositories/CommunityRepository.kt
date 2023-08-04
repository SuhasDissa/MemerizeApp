/*******************************************************************************
Created By Suhas Dissanayake on 8/4/23, 9:44 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import app.suhasdissa.memerize.backend.database.entity.AboutCommunity
import kotlinx.coroutines.flow.Flow

interface CommunityRepository<T : AboutCommunity> {
    fun getCommunities(): Flow<List<T>>
    suspend fun getCommunityInfo(community: T): T?
    suspend fun insertCommunity(community: T)
    suspend fun removeCommunity(community: T)
}
