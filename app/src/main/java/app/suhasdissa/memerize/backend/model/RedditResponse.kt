package app.suhasdissa.memerize.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reddit(
    @SerialName("data") val data: Data? = Data()
)

@Serializable
data class Data(
    @SerialName("children") val children: ArrayList<Children>? = arrayListOf()
)

@Serializable
data class Children(
    @SerialName("data") val childdata: ChildData? = ChildData()
)

@Serializable
data class ChildData(
    @SerialName("title") val title: String? = null,
    @SerialName("secure_media") val secure_media: SecureMedia? = SecureMedia(),
    @SerialName("url") val url: String? = null,
    @SerialName("permalink") val permalink: String? = null,
    @SerialName("preview") val preview: Preview? = Preview()

)

@Serializable
data class SecureMedia(
    @SerialName("reddit_video") val reddit_video: RedditVideo? = RedditVideo()
)

@Serializable
data class RedditVideo(
    @SerialName("dash_url") val dash_url: String? = null
)

@Serializable
data class Preview(
    @SerialName("images") val images: ArrayList<Images> = arrayListOf(),
    @SerialName("reddit_video_preview") val redditVideo: RedditVideo? = null
)

@Serializable
data class Images(
    @SerialName("source") val source: Source? = Source()
)

@Serializable
data class Source(
    @SerialName("url") val url: String? = null
)
