package app.suhasdissa.memerize.backend.serializables

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reddit(
    @SerialName("data") var data: Data
)

@Serializable
data class Data(
    @SerialName("children") var children: ArrayList<Children>
)

@Serializable
data class Children(
    @SerialName("data") var Childdata: ChildData
)

@Serializable
data class ChildData(
    @SerialName("secure_media") var secure_media: SecureMedia? = SecureMedia(),
    @SerialName("url") var url: String,
    @SerialName("permalink") var permalink: String? = null,
    @SerialName("preview") var preview: Preview? = Preview()

)

@Serializable
data class SecureMedia(
    @SerialName("reddit_video") var reddit_video: RedditVideo? = RedditVideo()
)

@Serializable
data class RedditVideo(
    @SerialName("dash_url") var dash_url: String? = null
)

@Serializable
data class Preview(
    @SerialName("images") var images: ArrayList<Images> = arrayListOf()
)

@Serializable
data class Images(
    @SerialName("source") var source: Source? = Source()
)

@Serializable
data class Source(
    @SerialName("url") var url: String? = null
)