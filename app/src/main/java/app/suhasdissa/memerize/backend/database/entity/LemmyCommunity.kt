/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 12:30 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "community", primaryKeys = ["community", "instance"])
data class LemmyCommunity(
    @ColumnInfo(name = "community") val community: String,
    @ColumnInfo(name = "instance") val instance: String,
    @ColumnInfo(name = "icon_url") val iconUrl: String?,
    @ColumnInfo(name = "name") val name: String
)
