package app.suhasdissa.memerize.backend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemerizeModel(
@SerialName("_id"   ) var Id    : String? = null,
@SerialName("link"  ) var link  : String? = null,
@SerialName("title" ) var title : String? = null
)