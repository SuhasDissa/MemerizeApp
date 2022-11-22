package app.suhasdissa.memerize.backend

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private val json = Json { ignoreUnknownKeys = true }

@OptIn(ExperimentalSerializationApi::class)
private val retrofitTG = Retrofit.Builder()
    .baseUrl("https://tg.i-c-a.su/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface TelegramApiService {
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
    @GET("json/{channel}")
    suspend fun getChannelData(
        @Path("channel") channel: String,
        @Query("limit") limit: String
    ): TelegramModel
}

object TelegramApi {
    val retrofitService: TelegramApiService by lazy {
        retrofitTG.create(TelegramApiService::class.java)
    }
}