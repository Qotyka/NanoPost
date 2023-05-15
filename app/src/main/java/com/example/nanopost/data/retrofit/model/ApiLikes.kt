package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class ApiLikes(
    val liked: Boolean,
    val likesCount: Int,
)
