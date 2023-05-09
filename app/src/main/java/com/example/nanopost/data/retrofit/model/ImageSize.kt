package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class ImageSize(
    val width: Int,
    val height: Int,
    val url: String,
)
