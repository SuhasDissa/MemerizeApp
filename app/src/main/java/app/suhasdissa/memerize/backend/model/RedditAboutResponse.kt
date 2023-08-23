package app.suhasdissa.memerize.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedditAboutResponse(
    @SerialName("data") val data: AboutData? = AboutData()
)

@Serializable
data class AboutData(
    @SerialName("community_icon") val communityIcon: String? = null,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("display_name_prefixed") val displayNamePrefixed: String? = null
) {
    val communityIconUrl
        get() = communityIcon?.replace("&amp;", "&")
}
