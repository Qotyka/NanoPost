package com.example.nanopost.data.retrofit

import com.example.nanopost.data.retrofit.model.*
import retrofit2.http.*

interface NanoPostApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body user: RegUser): TokenResponse
    @GET("/api/v1/profile/{profileId}")
    suspend fun getUser(@Path("profileId") id: String): ApiProfile
    @GET("/api/v1/posts/{profileId}")
    suspend fun getProfilePosts(
        @Path("profileId") profileId: String,
        @Query("count") count: Int,
        @Query("offset") offset: String?,
    ): PagedDataResponse<ApiPost>
    @GET("/api/auth/checkUsername")
    suspend fun checkUsername(@Query("username") username: String): ResultResponse
    @GET("/api/auth/login")
    suspend fun authUser(
        @Query("username") username: String,
        @Query("password") password: String,
    ): TokenResponse
}