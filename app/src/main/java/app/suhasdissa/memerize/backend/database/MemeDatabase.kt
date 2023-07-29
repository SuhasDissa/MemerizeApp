/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:42 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.suhasdissa.memerize.backend.database.dao.RedditMemeDao
import app.suhasdissa.memerize.backend.database.entity.RedditMeme

@Database(entities = [RedditMeme::class], version = 3)
abstract class MemeDatabase : RoomDatabase() {

    abstract fun redditMemeDao(): RedditMemeDao

    companion object {
        @Volatile
        private var INSTANCE: MemeDatabase? = null

        fun getDatabase(context: Context): MemeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemeDatabase::class.java,
                    "meme_database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
