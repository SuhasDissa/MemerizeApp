/*******************************************************************************
Created By Suhas Dissanayake on 8/4/23, 11:55 AM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.database.entity

interface Meme {
    val id: String
    val url: String
    val title: String
    val isVideo: Boolean
    val preview: String
}
