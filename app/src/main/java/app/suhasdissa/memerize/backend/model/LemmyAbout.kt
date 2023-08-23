package app.suhasdissa.memerize.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LemmyAbout(
    @SerialName("community_view") val communityView: CommunityView? = CommunityView()
)

@Serializable
data class CommunityView(
    @SerialName("community") val community: Community? = Community()
)

@Serializable
data class Community(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("icon") val icon: String? = null
)
