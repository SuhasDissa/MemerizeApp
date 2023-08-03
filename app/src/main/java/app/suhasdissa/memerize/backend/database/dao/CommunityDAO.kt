/*******************************************************************************
Created By Suhas Dissanayake on 8/3/23, 7:58 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.suhasdissa.memerize.backend.database.entity.LemmyCommunity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommunityDAO {
    @Query("SELECT * FROM community")
    fun getAll(): Flow<List<LemmyCommunity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(community: List<LemmyCommunity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(community: LemmyCommunity)

    @Delete
    fun delete(community: LemmyCommunity)
}
