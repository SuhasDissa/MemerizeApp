/*******************************************************************************
Created By Suhas Dissanayake on 11/24/22, 7:30 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import app.suhasdissa.memerize.backend.TelegramApi

interface TelegramRepository {
    suspend fun getData(channel: String): ArrayList<Meme>
}

private var MemeList: ArrayList<Meme> = arrayListOf()

class NetworkTelegramRepository : TelegramRepository {
    override suspend fun getData(channel: String): ArrayList<Meme> {
        MemeList = arrayListOf()
        val telegramData = TelegramApi.retrofitService.getChannelData(channel).messages
        telegramData.forEach { post ->

            if (post.media?.type?.contains("messageMediaPhoto") == true) {
                val url = "https://tg.i-c-a.su/media/$channel/${post.id}"
                MemeList.add(Meme(url, false, url))

            } else if (post.media?.type?.contains("messageMediaDocument") == true) {
                val url = "https://tg.i-c-a.su/media/$channel/${post.id}"
                val preview = "https://tg.i-c-a.su/media/$channel/${post.id}/preview"
                MemeList.add(Meme(url, true, preview))
            }

        }
        return MemeList
    }

}