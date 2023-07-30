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
import app.suhasdissa.memerize.backend.database.entity.Subreddit
import kotlinx.coroutines.flow.Flow

@Dao
interface SubredditDAO {
    @Query("SELECT * FROM subreddit")
    fun getAll(): Flow<List<Subreddit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(subreddits: List<Subreddit>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subreddits: Subreddit)

    @Delete
    fun delete(subreddit: Subreddit)
}
