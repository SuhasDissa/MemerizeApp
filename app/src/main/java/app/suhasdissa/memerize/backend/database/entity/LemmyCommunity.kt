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
    @ColumnInfo(name = "community") override val id: String,
    @ColumnInfo(name = "instance") val instance: String,
    @ColumnInfo(name = "icon_url") override val iconUrl: String? = null,
    @ColumnInfo(name = "name") override val name: String = ""
) : AboutCommunity
