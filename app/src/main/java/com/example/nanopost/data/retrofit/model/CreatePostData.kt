package com.example.nanopost.data.retrofit.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.File

data class CreatePostData(
    val text: String? = null,
    val images: List<Bitmap>,
)
