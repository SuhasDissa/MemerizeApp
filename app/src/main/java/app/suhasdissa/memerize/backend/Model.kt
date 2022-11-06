package app.suhasdissa.memerize.backend

import androidx.compose.ui.res.stringResource
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

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
    @SerialName("url") var url: String
    /*@SerialName("title") var title: String? = null,
    @SerialName("preview") var preview: Preview? = Preview(),
    @SerialName("thumbnail") var thumbnail: String? = null,
    @SerialName("media_only") var mediaOnly: Boolean? = null,
    @SerialName("permalink") var permalink: String? = null,
    @SerialName("media") var media: String? = null,
    @SerialName("is_video") var isVideo: Boolean? = null*/

)
/*
@Serializable
data class Source(
    @SerialName("url") var url: String? = null,
    @SerialName("width") var width: Int? = null,
    @SerialName("height") var height: Int? = null

)

@Serializable
data class Preview(
    @SerialName("images") var images: ArrayList<Images> = arrayListOf(),
    @SerialName("enabled") var enabled: Boolean? = null

)

@Serializable
data class Images(
    @SerialName("source") var source: Source? = Source(),
    @SerialName("resolutions") var resolutions: ArrayList<Resolutions> = arrayListOf()

)

@Serializable
data class Resolutions(
    @SerialName("url") var url: String? = null,
)
*/