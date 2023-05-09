package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class ApiPost(
    val id: String,
    val text: String?,
)
