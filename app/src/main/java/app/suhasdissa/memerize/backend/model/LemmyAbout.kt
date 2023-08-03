/*******************************************************************************
Created By Suhas Dissanayake on 8/3/23, 6:38 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LemmyAbout(
    @SerialName("community_view") var communityView: CommunityView? = CommunityView()
)

@Serializable
data class CommunityView(
    @SerialName("community") var community: Community? = Community()
)

@Serializable
data class Community(
    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("icon") var icon: String? = null
)
