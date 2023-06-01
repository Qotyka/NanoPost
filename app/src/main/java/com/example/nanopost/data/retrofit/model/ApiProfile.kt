package com.example.nanopost.data.retrofit.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiProfile(
    val id: String,
    val username: String,
    val displayName: String? = null,
    val avatarId: String? = null,
    val avatarSmall: String? = null,
    val avatarLarge: String? = null,
    val bio: String? = null,
    val subscribed: Boolean,
    val subscribersCount: Int,
    val postsCount: Int,
    val imagesCount: Int,
    val images: List<ApiImage>
)