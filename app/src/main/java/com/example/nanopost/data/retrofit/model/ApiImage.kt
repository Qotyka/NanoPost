package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class ApiImage(
    val id: String,
    val owner: ProfileCompact,
    val dateCreated: Long,
    val sizes: List<ImageSize>,
)
