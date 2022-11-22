package app.suhasdissa.memerize.backend.serializables

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemerizeModel(
    @SerialName("_id") var Id: String,
    @SerialName("title") var title: String,
    @SerialName("content") var content: String
)