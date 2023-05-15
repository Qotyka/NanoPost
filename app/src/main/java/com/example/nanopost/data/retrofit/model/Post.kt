package com.example.nanopost.data.retrofit.model

import androidx.annotation.DrawableRes
import java.time.LocalDateTime

data class Post(
    val id: String,
    val sender: String?,
    val text: String?,
    val dateCreated: Long,
    val images: List<ImageData>,
)
