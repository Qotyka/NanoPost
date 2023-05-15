package com.example.nanopost.data.retrofit.model

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

data class ImageData(
    val id: String,
    val owner: ProfileCompact,
    val dateCreated: Long,
    val sizes: List<ImageSize>
)
