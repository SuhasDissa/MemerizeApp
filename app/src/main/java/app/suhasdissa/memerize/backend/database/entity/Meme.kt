package app.suhasdissa.memerize.backend.database.entity

interface Meme {
    val id: String
    val url: String
    val title: String
    val isVideo: Boolean
    val preview: String
    val postLink: String?
}
