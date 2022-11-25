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

@Entity
data class Meme(
    @PrimaryKey val url: String,
    @ColumnInfo(name = "isVideo") val isVideo: Boolean,
    @ColumnInfo(name = "preview") val preview: String?
)