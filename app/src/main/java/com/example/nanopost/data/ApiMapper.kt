package com.example.nanopost.data

import com.example.nanopost.data.retrofit.model.*
import java.time.LocalDateTime
import java.time.ZoneId

fun ApiPost.toPost(): Post {
    return Post(
        id = id,
        sender = owner.displayName,
        dateCreated = dateCreated,
        text = text,
        images = images.map{image ->
            image.toImageData()
        }
    )
}

fun ApiProfile.toProfile(): Profile {
    return Profile(
        id = id,
        username = username,
        bio = bio,
        subscribersCount = subscribersCount,
        imagesCount = imagesCount,
        postsCount = postsCount,
        avatar = avatarId,
        displayName = displayName,
        images = images.map{image ->
            image.toImageData()
        }
    )
}

fun ApiImage.toImageData(): ImageData {
    return ImageData(
        dateCreated = dateCreated,
        sizes = sizes,
        id = id,
        owner = owner,
    )
}