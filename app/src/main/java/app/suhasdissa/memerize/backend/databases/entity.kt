/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 7:39 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reddit_table")
data class RedditMeme(
    @PrimaryKey val url: String,
    @ColumnInfo(name = "is_video") val isVideo: Boolean,
    @ColumnInfo(name = "preview", defaultValue = "") val preview: String,
    @ColumnInfo(name = "subreddit", defaultValue = "") val subreddit: String
)

@Entity(tableName = "telegram_table")
data class TelegramMeme(
    @PrimaryKey val url: String,
    @ColumnInfo(name = "is_video") val isVideo: Boolean,
    @ColumnInfo(name = "preview", defaultValue = "") val preview: String,
    @ColumnInfo(name = "channel", defaultValue = "") val channel: String
)