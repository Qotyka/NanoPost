package com.example.nanopost.data.retrofit.model

data class Profile (
    val id: String,
    val username: String,
    val bio: String?,
    val subscribersCount: Int,
    )