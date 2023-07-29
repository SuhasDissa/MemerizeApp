/*******************************************************************************
 Created By Suhas Dissanayake on 7/29/23, 8:14 PM
 Copyright (c) 2023
 https://github.com/SuhasDissa/
 All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reddit_table")
data class RedditMeme(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title", defaultValue = "") val title: String,
    @ColumnInfo(name = "is_video") val isVideo: Boolean,
    @ColumnInfo(name = "preview", defaultValue = "") val preview: String,
    @ColumnInfo(name = "subreddit", defaultValue = "") val subreddit: String
)
