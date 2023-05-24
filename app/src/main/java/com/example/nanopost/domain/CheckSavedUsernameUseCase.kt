package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import javax.inject.Inject

class CheckSavedUsernameUseCase @Inject constructor(
    private val repository: RemoteDataRepository
) {

    operator fun invoke(username: String) = repository.checkSavedUsername(username)
}