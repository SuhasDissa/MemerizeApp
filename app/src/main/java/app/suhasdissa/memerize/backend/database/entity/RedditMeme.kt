package app.suhasdissa.memerize.backend.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reddit_table")
data class RedditMeme(
    @PrimaryKey override val id: String,
    @ColumnInfo(name = "url") override val url: String,
    @ColumnInfo(name = "title", defaultValue = "") override val title: String,
    @ColumnInfo(name = "is_video") override val isVideo: Boolean,
    @ColumnInfo(name = "preview", defaultValue = "") override val preview: String,
    @ColumnInfo(name = "subreddit", defaultValue = "") val subreddit: String,
    @ColumnInfo(name = "post_link", defaultValue = "NULL") override val postLink: String?
) : Meme
