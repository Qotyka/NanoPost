package com.example.nanopost.data

import androidx.annotation.DrawableRes
import java.time.LocalDateTime

data class TextViewData(
    val id: String,
    val dateCreated: LocalDateTime,
    val text: String?,
    @DrawableRes
    val imageRes: Int?,
    val likes: Int
)
