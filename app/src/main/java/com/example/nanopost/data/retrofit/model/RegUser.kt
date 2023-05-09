package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class RegUser(
    val username: String,
    val password: String,
)