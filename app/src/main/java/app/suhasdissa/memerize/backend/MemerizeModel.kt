package app.suhasdissa.memerize.backend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemerizeModel(
@SerialName("_id"   ) var Id    : String,
@SerialName("link"  ) var link  : String,
@SerialName("title" ) var title : String
)