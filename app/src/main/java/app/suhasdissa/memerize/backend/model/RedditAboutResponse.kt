/*******************************************************************************
Created By Suhas Dissanayake on 7/29/23, 11:25 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedditAboutResponse(
    @SerialName("data") var data: AboutData? = AboutData()
)

@Serializable
data class AboutData(
    @SerialName("community_icon") var communityIcon: String? = null,
    @SerialName("display_name") var displayName: String? = null,
    @SerialName("display_name_prefixed") var displayNamePrefixed: String? = null
) {
    val communityIconUrl
        get() = communityIcon?.replace("&amp;", "&")
}
