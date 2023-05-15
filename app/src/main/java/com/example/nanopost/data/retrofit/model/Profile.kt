package com.example.nanopost.data.retrofit.model

import android.graphics.drawable.Drawable

data class Profile (
    val id: String,
    val username: String,
    val bio: String?,
    val avatar: String?,
    val displayName: String?,
    val postsCount: Int,
    val imagesCount: Int,
    val subscribersCount: Int,
    val images: List<ImageData>
    )