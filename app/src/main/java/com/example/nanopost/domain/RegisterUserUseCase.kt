package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.TokenResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke(username: String, password: String): Flow<String> {
        return flow{
            emit(repository.registerUser(username, password))
        }
    }
}