package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {
    operator fun invoke(postId: String) = repository.deletePost(postId)
}