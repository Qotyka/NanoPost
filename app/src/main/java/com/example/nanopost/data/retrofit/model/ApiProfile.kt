package com.example.nanopost.data.retrofit.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiProfile(
    val id: String,
    val username: String,
    val displayName: String?,
    val avatarId: String?,
    val avatarSmall: String?,
    val avatarLarge: String?,
    val bio: String?,
    val subscribed: Boolean,
    val subscribersCount: Int,
    val postsCount: Int,
    val imagesCount: Int,
    val images: List<ApiImage>
)