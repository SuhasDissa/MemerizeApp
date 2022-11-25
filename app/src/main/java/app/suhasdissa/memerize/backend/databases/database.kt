/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:42 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.databases

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Meme::class], version = 1)
abstract class MemeDatabase : RoomDatabase() {

    abstract fun memeDao(): MemeDao

    companion object {
        @Volatile
        private var INSTANCE: MemeDatabase? = null

        fun getDatabase(context: Context): MemeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MemeDatabase::class.java, "meme_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class MemeRepository(private val memeDao: MemeDao) {
    val allMemes = memeDao.getAll()

    @WorkerThread
    fun insert(memes: ArrayList<Meme>) {
        memeDao.insertAll(memes)
    }
}