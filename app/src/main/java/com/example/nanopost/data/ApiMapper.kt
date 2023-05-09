package com.example.nanopost.data

import com.example.nanopost.data.retrofit.model.*

fun ApiPost.toPost(): Post {
    return Post(
        id = id,
        sender = "",
        text = text,
        imageRes = null,
        likes = 0,
    )
}

fun ApiProfile.toProfile(): Profile {
    return Profile(
        id = id,
        username = username,
        bio = bio,
        subscribersCount = subscribersCount
    )
}