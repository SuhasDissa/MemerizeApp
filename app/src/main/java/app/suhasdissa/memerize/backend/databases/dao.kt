/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:40 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemeDao {
    @Query("SELECT * FROM meme")
    fun getAll(): List<Meme>

    @Insert
    fun insertAll(memes: ArrayList<Meme>)

    @Delete
    fun delete(meme: Meme)
}
