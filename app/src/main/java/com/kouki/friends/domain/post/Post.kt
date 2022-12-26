package com.kouki.friends.domain.post

data class Post(
    val postId: String,
    val userId: String,
    val postText: String,
    val timestamp: Long,
)
