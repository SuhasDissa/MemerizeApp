/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.apis

import app.suhasdissa.memerize.backend.serializables.TelegramModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

private val json = Json { ignoreUnknownKeys = true }

@OptIn(ExperimentalSerializationApi::class)
private val retrofitTG = Retrofit.Builder()
    .baseUrl("https://tg.i-c-a.su/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface TelegramApiService {
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
    @GET("json/{channel}?limit=20")
    suspend fun getChannelData(
        @Path("channel") channel: String
    ): TelegramModel
}

object TelegramApi {
    val retrofitService: TelegramApiService by lazy {
        retrofitTG.create(TelegramApiService::class.java)
    }
}