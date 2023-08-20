package app.suhasdissa.memerize.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LemmyModel(
    @SerialName("posts") var posts: ArrayList<Posts> = arrayListOf()
)

@Serializable
data class Posts(
    @SerialName("post") var post: Post? = Post()
)

@Serializable
data class Post(
    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("thumbnail_url") var thumbnailUrl: String? = null,
    @SerialName("ap_id") var postLink: String? = null
)
