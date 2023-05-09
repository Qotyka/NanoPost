package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class ProfileCompact(
    val id: String,
    val username: String,
    val displayName: String?,
    val avatarUrl: String?,
    val subscribed: Boolean,
)
