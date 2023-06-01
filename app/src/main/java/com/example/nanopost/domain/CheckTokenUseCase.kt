package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import javax.inject.Inject

class CheckTokenUseCase @Inject constructor(
    private val repository: RemoteDataRepository
) {

    operator fun invoke() = repository.checkToken()
}