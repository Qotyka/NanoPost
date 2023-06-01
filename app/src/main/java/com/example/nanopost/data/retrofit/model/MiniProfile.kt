package com.example.nanopost.data.retrofit.model

data class MiniProfile(
    val id: String,
    val username: String,
    val displayName: String,
    val avatarUrl: String? = null,
    val subscribed: Boolean,
)