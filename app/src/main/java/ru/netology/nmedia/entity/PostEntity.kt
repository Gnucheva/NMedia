package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val video: String,
    val videoName: String,
    val likes: Long = 0,
    val share: Long = 0,
    val views: Long = 0,
    val likedByMe: Boolean
) {
    fun toPost()=
        Post(id, author, content, published, video, videoName, likes, share, views, likedByMe)


    companion object {
        fun fromPost(post: Post) = PostEntity(
            post.id,
            post.author,
            post.content,
            post.published,
            post.video,
            post.videoName,
            post.likes,
            post.share,
            post.views,
            post.likedByMe
        )
    }
}
