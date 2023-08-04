/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 12:36 PM
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
import app.suhasdissa.memerize.backend.database.entity.RedditCommunity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubredditDAO {
    @Query("SELECT * FROM subreddit")
    fun getAll(): Flow<List<RedditCommunity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(subreddits: List<RedditCommunity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subreddits: RedditCommunity)

    @Delete
    fun delete(subreddit: RedditCommunity)
}
