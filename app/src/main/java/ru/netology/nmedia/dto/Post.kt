package ru.netology.nmedia.dto

data class Post(
    val id: Long = 0,
    val author: String = "",
    val content: String = "",
    val published: String = "",
    val likes: Long = 0,
    val share: Long = 0,
    val views: Long = 0,
    val likedByMe: Boolean = false

)