package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import javax.inject.Inject

class UnsubscribeFromUserUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {
    operator fun invoke(username: String) = repository.unsubscribeFromUser(username)
}