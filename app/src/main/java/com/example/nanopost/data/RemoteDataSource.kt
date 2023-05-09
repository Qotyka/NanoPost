package com.example.nanopost.data

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.nanopost.data.paging.PostPagingSource
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.data.retrofit.model.*
import com.example.nanopost.di.ApiService
import com.example.nanopost.di.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    @ApiService private val apiService: NanoPostApiService,
    @AuthService private val authService: NanoPostApiService,
    private val preferences: SharedPreferences,
) {

    private suspend fun checkUsername(username: String): ResultResponse{
        return apiService.checkUsername(username)
    }

    suspend fun registerUser(username: String, password: String): String{
        val checkResult = checkUsername(username)
        return when(checkResult.result){
            "Free" -> {
                apiService.registerUser(RegUser(
                    username = username,
                    password = password,
                )).apply {
                    preferences.edit {
                        putString("USERNAME", username)
                        putString("USERID", userId)
                        putString("TOKEN", token)
                    }
                }
                checkResult.result
            }
            else -> checkResult.result
        }
    }

    suspend fun authUser(username: String, password: String): String{
        val checkResult = checkUsername(username)
        return when(checkResult.result){
            "Free" -> {
                apiService.authUser(
                    username = username,
                    password = password,
                ).apply {
                    preferences.edit {
                        putString("USERNAME", username)
                        putString("USERID", userId)
                        putString("TOKEN", token)
                    }
                }
                checkResult.result
            }
            else -> checkResult.result
        }
    }

    fun getProfile(userId: String): Flow<Profile> {
        return flow{
            emit(apiService.getUser(userId).toProfile())
        }
    }

    fun getProfilePosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(apiService) }
        ).flow.map{ pagingData->
            pagingData.map { it.toPost() }
        }
    }
}