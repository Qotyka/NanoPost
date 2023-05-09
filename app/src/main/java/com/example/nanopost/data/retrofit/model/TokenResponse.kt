package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class TokenResponse(
    val token: String,
    val userId: String,
)