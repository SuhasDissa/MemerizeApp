/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:42 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.suhasdissa.memerize.backend.database.dao.CommunityDAO
import app.suhasdissa.memerize.backend.database.dao.LemmyMemeDAO
import app.suhasdissa.memerize.backend.database.dao.RedditMemeDao
import app.suhasdissa.memerize.backend.database.dao.SubredditDAO
import app.suhasdissa.memerize.backend.database.entity.LemmyCommunity
import app.suhasdissa.memerize.backend.database.entity.LemmyMeme
import app.suhasdissa.memerize.backend.database.entity.RedditCommunity
import app.suhasdissa.memerize.backend.database.entity.RedditMeme

@Database(
    entities = [RedditMeme::class, RedditCommunity::class, LemmyMeme::class, LemmyCommunity::class],
    version = 5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 4, to = 5)
    ]
)
abstract class MemeDatabase : RoomDatabase() {

    abstract fun redditMemeDao(): RedditMemeDao
    abstract fun subredditDao(): SubredditDAO
    abstract fun communityDao(): CommunityDAO
    abstract fun lemmyMemeDao(): LemmyMemeDAO

    companion object {
        @Volatile
        private var INSTANCE: MemeDatabase? = null

        fun getDatabase(context: Context): MemeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemeDatabase::class.java,
                    "meme_database"
                ).allowMainThreadQueries()
                    .addCallback(initSubreddits())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        class initSubreddits : Callback() {
            val redditList = listOf(
                RedditCommunity(
                    "maybemaybemaybe",
                    "https://styles.redditmedia.com/t5_38e1l/styles/communityIcon_hcpveq6pu5p41.png",
                    "Maybe Maybe Maybe"
                ),
                RedditCommunity(
                    "holup",
                    "https://styles.redditmedia.com/t5_qir9n/styles/communityIcon_yvasg0bnblaa1.png",
                    "HolUP"
                ),
                RedditCommunity(
                    "funny",
                    "https://a.thumbs.redditmedia.com/kIpBoUR8zJLMQlF8azhN-kSBsjVUidHjvZNLuHDONm8.png",
                    "Funny"
                ),
                RedditCommunity(
                    "facepalm",
                    "https://styles.redditmedia.com/t5_2r5rp/styles/communityIcon_qzjxzx1g08z91.jpg",
                    "FacePalm"
                ),
                RedditCommunity(
                    "memes",
                    "https://styles.redditmedia.com/t5_2qjpg/styles/communityIcon_uzvo7sibvc3a1.jpg",
                    "Memes"
                ),
                RedditCommunity(
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
