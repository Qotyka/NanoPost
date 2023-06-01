package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class EditProfileData(
    val displayName: String?,
    val bio: String?,
    val avatar: String?,
)