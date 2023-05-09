package com.example.nanopost.data.retrofit.model

@kotlinx.serialization.Serializable
data class PagedDataResponse<T>(
    val count: Int,
    val total: Int,
    val offset: String? = null,
    val items: List<T>,
)
