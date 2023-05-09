package com.example.nanopost.data.retrofit.model

import androidx.annotation.DrawableRes
import java.time.LocalDateTime

data class Post(
    val id: String,
    val sender: String,
    val text: String?,
    @DrawableRes
    val imageRes: Int?,
    val likes: Int
)
