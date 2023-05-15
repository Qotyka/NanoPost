package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class ApiPost(
    val id: String,
    val text: String? = null,
    val dateCreated: Long,
    val owner: ProfileCompact,
    val images: List<ApiImage>,
    val likes: ApiLikes
)
