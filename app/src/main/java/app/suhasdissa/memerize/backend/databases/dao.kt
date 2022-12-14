/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:40 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.databases

import androidx.room.*

@Dao
interface RedditMemeDao {
    @Query("SELECT * FROM reddit_table WHERE subreddit=:subreddit")
    fun getAll(subreddit:String): List<RedditMeme>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(memes: List<RedditMeme>)

    @Delete
    fun delete(meme: RedditMeme)
}

@Dao
interface TelegramMemeDao {
    @Query("SELECT * FROM telegram_table WHERE channel=:channel")
    fun getAll(channel:String): List<TelegramMeme>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(memes: List<TelegramMeme>)

    @Delete
    fun delete(meme: TelegramMeme)
}
