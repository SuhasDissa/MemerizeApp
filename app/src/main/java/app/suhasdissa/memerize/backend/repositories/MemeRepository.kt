/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import app.suhasdissa.memerize.backend.database.entity.AboutCommunity
import app.suhasdissa.memerize.backend.database.entity.Meme

interface MemeRepository<T : Meme, C : AboutCommunity> {
    suspend fun getOnlineData(community: C, time: String): List<T>?
    suspend fun getLocalData(community: C): List<T>
}
