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
import androidx.sqlite.db.SupportSQLiteDatabase
import app.suhasdissa.memerize.backend.database.dao.RedditMemeDao
import app.suhasdissa.memerize.backend.database.dao.SubredditDAO
import app.suhasdissa.memerize.backend.database.entity.RedditMeme
import app.suhasdissa.memerize.backend.database.entity.Subreddit

@Database(entities = [RedditMeme::class, Subreddit::class], version = 4)
abstract class MemeDatabase : RoomDatabase() {

    abstract fun redditMemeDao(): RedditMemeDao
    abstract fun subredditDao(): SubredditDAO

    companion object {
        @Volatile
        private var INSTANCE: MemeDatabase? = null

        fun getDatabase(context: Context): MemeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemeDatabase::class.java,
                    "meme_database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .addCallback(initSubreddits())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        class initSubreddits : Callback() {
            val redditList = listOf(
                Subreddit(
                    "maybemaybemaybe",
                    "https://styles.redditmedia.com/t5_38e1l/styles/communityIcon_hcpveq6pu5p41.png",
                    "Maybe Maybe Maybe"
                ),
                Subreddit(
                    "holup",
                    "https://styles.redditmedia.com/t5_qir9n/styles/communityIcon_yvasg0bnblaa1.png",
                    "HolUP"
                ),
                Subreddit(
                    "funny",
                    "https://a.thumbs.redditmedia.com/kIpBoUR8zJLMQlF8azhN-kSBsjVUidHjvZNLuHDONm8.png",
                    "Funny"
                ),
                Subreddit(
                    "facepalm",
                    "https://styles.redditmedia.com/t5_2r5rp/styles/communityIcon_qzjxzx1g08z91.jpg",
                    "FacePalm"
                ),
                Subreddit(
                    "memes",
                    "https://styles.redditmedia.com/t5_2qjpg/styles/communityIcon_uzvo7sibvc3a1.jpg",
                    "Memes"
                ),
                Subreddit(
                    "dankmemes",
                    "https://styles.redditmedia.com/t5_2zmfe/styles/communityIcon_g5xoywnpe2l91.png",
                    "Dank Memes"
                )
            )

            override fun onCreate(db: SupportSQLiteDatabase) {
                redditList.forEach {
                    db.execSQL(
                        "INSERT INTO subreddit (id, icon_url, name) " +
                            "VALUES ('${it.id}', '${it.iconUrl}', '${it.name}');"
                    )
                }
            }
        }
    }
}
