package app.suhasdissa.memerize.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LemmyResponse(
    @SerialName("posts") val posts: ArrayList<Posts> = arrayListOf()
)

@Serializable
data class Posts(
    @SerialName("post") val post: Post? = Post()
)

@Serializable
data class Post(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerialName("ap_id") val postLink: String? = null
)
