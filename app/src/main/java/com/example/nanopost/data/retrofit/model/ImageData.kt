package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class ImageData(
    val id: String,
    val text: String?,
    val owner: ProfileCompact,
    val dateCreated: Long,
)
