package app.suhasdissa.memerize.backend

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers

private val BASE_URL = "https://www.reddit.com/"
@OptIn(ExperimentalSerializationApi::class)
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
    //.addConverterFactory(ScalarsConverterFactory.create())
    .build()

interface ApiService{
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
    @GET("r/tkasylum/top/.json")
    suspend fun getPhotos(): Reddit
}
object MemeApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}