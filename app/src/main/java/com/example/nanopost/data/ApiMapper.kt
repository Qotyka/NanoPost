package com.example.nanopost.data

import com.example.nanopost.data.retrofit.model.*
import java.time.LocalDateTime
import java.time.ZoneId

fun ApiPost.toPost(): Post {
    return Post(
        id = id,
        sender = owner.displayName ?: owner.username,
        dateCreated = dateCreated,
        text = text,
        images = images.map{image ->
            image.toImageData()
        },
        liked = likes.liked,
        likesCount = likes.likesCount,
        avatarUrl = owner.avatarUrl
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
        avatarUrlSmall = avatarSmall,
        avatarUrlLarge = avatarLarge,
        displayName = displayName ?: username,
        images = images.map{image ->
            image.toImageData()
        },
        subscribed = subscribed,
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

fun ProfileCompact.toMiniProfile(): MiniProfile {
    return MiniProfile(
        id = id,
        username = username,
        displayName = displayName ?: username,
        avatarUrl = avatarUrl,
        subscribed = subscribed,
    )
}