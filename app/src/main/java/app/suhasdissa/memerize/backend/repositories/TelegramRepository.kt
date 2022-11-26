/*******************************************************************************
Created By Suhas Dissanayake on 11/24/22, 7:30 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.repositories

import androidx.annotation.WorkerThread
import app.suhasdissa.memerize.backend.apis.TelegramApi
import app.suhasdissa.memerize.backend.databases.TelegramMeme
import app.suhasdissa.memerize.backend.databases.TelegramMemeDao

interface TelegramRepository {
    suspend fun getData(channel: String): ArrayList<Meme>
}

class NetworkTelegramRepository(private val telegramMemeDao: TelegramMemeDao) : TelegramRepository {

    override suspend fun getData(channel: String): ArrayList<Meme> {
        var memesList: ArrayList<Meme>
        try {
            memesList = getNetworkData(channel)
            Thread {
                insertMemes(memesList.map { TelegramMeme(it.url, it.isVideo, it.preview) })
            }.start()
        } catch (e: Exception) {
            memesList = getLocalData()
        }
        return memesList
    }

    private suspend fun getNetworkData(channel: String): ArrayList<Meme> {
        val memeList: ArrayList<Meme> = arrayListOf()
        val telegramData = TelegramApi.retrofitService.getChannelData(channel).messages
        telegramData.forEach { post ->
            if (post.media?.type?.contains("messageMediaPhoto") == true) {
                val url = "https://tg.i-c-a.su/media/$channel/${post.id}"
                memeList.add(Meme(url, false, url))
            } else if (post.media?.type?.contains("messageMediaDocument") == true) {
                val url = "https://tg.i-c-a.su/media/$channel/${post.id}"
                val preview = "https://tg.i-c-a.su/media/$channel/${post.id}/preview"
                memeList.add(Meme(url, true, preview))
            }
        }
        return memeList
    }

    private fun getLocalData(): ArrayList<Meme> {
        return telegramMemeDao.getAll().mapTo(ArrayList()) {
            Meme(
                it.url, it.isVideo, it.preview
            )
        }
    }

    @WorkerThread
    private fun insertMemes(memes: List<TelegramMeme>) {
        telegramMemeDao.insertAll(memes)
    }
}