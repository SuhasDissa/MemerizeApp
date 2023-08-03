/*******************************************************************************
Created By Suhas Dissanayake on 8/3/23, 8:01 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.suhasdissa.memerize.backend.database.entity.LemmyMeme

@Dao
interface LemmyMemeDAO {
    @Query("SELECT * FROM lemmy_table WHERE community=:community AND instance=:instance")
    fun getAll(community: String, instance: String): List<LemmyMeme>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(memes: List<LemmyMeme>)
}
