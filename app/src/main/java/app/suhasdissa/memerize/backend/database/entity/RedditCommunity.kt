/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 12:30 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subreddit")
data class RedditCommunity(
    @PrimaryKey override val id: String,
    @ColumnInfo(name = "icon_url") override val iconUrl: String? = null,
    @ColumnInfo(name = "name") override val name: String = ""
) : AboutCommunity
