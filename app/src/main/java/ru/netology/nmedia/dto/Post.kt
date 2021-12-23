package ru.netology.nmedia.dto

data class Post(
    val id: Long = 0,
    val author: String = "",
    val content: String = "",
    val published: String = "",
    var likes: Long = 0,
    var shares: Long = 0,
    var views: Long = 0,
    var likedByMe: Boolean = false

)