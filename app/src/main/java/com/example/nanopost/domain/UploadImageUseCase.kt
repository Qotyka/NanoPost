package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {
    operator fun invoke(image: String) = repository.uploadImage(image)
}