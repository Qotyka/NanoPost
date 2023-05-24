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

    @GET("/api/v1/feed")
    suspend fun getFeed(
        @Query("count") count: Int,
        @Query("offset") offset: String?,
    ): PagedDataResponse<ApiPost>

    @GET("/api/v1/profile/search")
    suspend fun getProfiles(
        @Query("query") query: String,
        @Query("count") count: Int,
        @Query("offset") offset: String?,
    ): PagedDataResponse<ProfileCompact>

    @GET("/api/v1/images/{profileId}")
    suspend fun getProfileImages(
        @Path("profileId") profileId: String,
        @Query("count") count: Int,
        @Query("offset") offset: String?,
    ): PagedDataResponse<ApiImage>

    @GET("/api/v1/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String,
    ): ApiPost

    @GET("/api/auth/checkUsername")
    suspend fun checkUsername(@Query("username") username: String): ResultResponseCheckUsername

    @GET("/api/auth/login")
    suspend fun authUser(
        @Query("username") username: String,
        @Query("password") password: String,
    ): TokenResponse

    @GET("/api/v1/image/{imageId}")
    suspend fun getImage(@Path("imageId") imageId: String): ApiImage

    @PUT("/api/v1/profile/{profileId}/subscribe")
    suspend fun subscribe(@Path("profileId") profileId: String): ResultResponse

    @DELETE("/api/v1/profile/{profileId}/subscribe")
    suspend fun unsubscribe(@Path("profileId") profileId: String): ResultResponse

}