package app.suhasdissa.memerize.backend

import app.suhasdissa.memerize.backend.serializables.MemerizeModel
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
private val retrofitVideo = Retrofit.Builder()
    .baseUrl("https://memerize-app-default-rtdb.asia-southeast1.firebasedatabase.app/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface DesertApiService {
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
    @GET("{collection}.json")
    suspend fun getData(
        @Path("collection") collection: String
    ): List<MemerizeModel>
}

object MemerizeApi {
    val retrofitService: DesertApiService by lazy {
        retrofitVideo.create(DesertApiService::class.java)
    }
}