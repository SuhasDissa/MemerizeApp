/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:42 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RedditMeme::class, TelegramMeme::class], version = 2)
abstract class MemeDatabase : RoomDatabase() {

    abstract fun redditMemeDao(): RedditMemeDao
    abstract fun telegramMemeDao(): TelegramMemeDao

    companion object {
        @Volatile
        private var INSTANCE: MemeDatabase? = null

        fun getDatabase(context: Context): MemeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MemeDatabase::class.java, "meme_database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}