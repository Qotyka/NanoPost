package com.example.nanopost.data.retrofit

import com.example.nanopost.data.retrofit.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.ByteString
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

    @PUT("/api/v1/image")
    suspend fun uploadImage(@Body image: String): ApiImage

    @Multipart
    @PUT("/api/v1/post")
    suspend fun createPost(
        @Part text: MultipartBody.Part?,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
    ): ApiPost

    @DELETE("/api/v1/post/{postId}")
    suspend fun deletePost(@Path("postId") postId: String): ResultResponse

    @DELETE("/api/v1/image/{imageId}")
    suspend fun deleteImage(@Path("imageId") imageId: String): ResultResponse

    @PATCH("/api/v1/profile/{profileId}")
    suspend fun editProfile(
        @Path("profileId") profileId: String,
        @Body profileData: EditProfileData,
    ): ResultResponse

    @DELETE("/api/v1/profile/{profileId}/subscribe")
    suspend fun unsubscribe(@Path("profileId") profileId: String): ResultResponse

}