package app.suhasdissa.memerize.backend

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
    @SerialName("url") var url: String,
    @SerialName("permalink") var permalink: String? = null,
    @SerialName("preview") var preview: Preview? = Preview()
    /*@SerialName("title") var title: String? = null,
    @SerialName("preview") var preview: Preview? = Preview(),
    @SerialName("thumbnail") var thumbnail: String? = null,
    @SerialName("media_only") var mediaOnly: Boolean? = null,
    @SerialName("permalink") var permalink: String? = null,
    @SerialName("media") var media: String? = null,
    @SerialName("is_video") var isVideo: Boolean? = null*/

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