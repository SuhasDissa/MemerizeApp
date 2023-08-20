package app.suhasdissa.memerize.backend.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lemmy_table")
data class LemmyMeme(
    @PrimaryKey override val id: String,
    @ColumnInfo(name = "url") override val url: String,
    @ColumnInfo(name = "title", defaultValue = "") override val title: String,
    @ColumnInfo(name = "is_video") override val isVideo: Boolean,
    @ColumnInfo(name = "preview", defaultValue = "") override val preview: String,
    @ColumnInfo(name = "community", defaultValue = "") val community: String,
    @ColumnInfo(name = "instance", defaultValue = "") val instance: String,
    @ColumnInfo(name = "post_link", defaultValue = "NULL") override val postLink: String?
) : Meme
