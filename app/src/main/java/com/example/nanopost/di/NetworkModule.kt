package com.example.nanopost.di

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.Chucker
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.RemoteDataSource
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.domain.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class AuthRetrofit
@Qualifier
annotation class ApiRetrofit
@Qualifier
annotation class ApiService
@Qualifier
annotation class AuthService
@Qualifier
annotation class AuthInterceptor


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideJson(): Json{
        return Json { ignoreUnknownKeys = true }
    }

    @Singleton
    @Provides
    fun provideMediaType(): MediaType{
        return "application/json".toMediaType()
    }

    @AuthInterceptor
    @Singleton
    @Provides
    fun provideAuthInterceptor(preferences: SharedPreferences): Interceptor{
        return Interceptor{chain ->
            val originalRequest = chain.request()
            val token = preferences.getString("TOKEN", null)
            val requestBuilder = originalRequest.newBuilder()
            if(token !== null){
                requestBuilder.header("Authorization","Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }
    }

    @Singleton
    @Provides
    fun provideApiClient(
        @AuthInterceptor authInterceptor: Interceptor,
        @ApplicationContext context: Context,
        ): OkHttpClient{
            return OkHttpClient().newBuilder()
                .addInterceptor(ChuckerInterceptor.Builder(context)
                                .build())
                .addInterceptor(authInterceptor)
                .build()
    }

    @AuthRetrofit
    @Singleton
    @Provides
    fun provideRetrofitAuthService(contentType: MediaType, client: OkHttpClient, json: Json): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://nanopost.evolitist.com")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @ApiRetrofit
    @Singleton
    @Provides
    fun provideApiRetrofit(contentType: MediaType, json: Json): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://nanopost.evolitist.com")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @ApiService
    @Singleton
    @Provides
    fun provideApiService(@ApiRetrofit retrofitApiService: Retrofit): NanoPostApiService {
        return retrofitApiService.create(NanoPostApiService::class.java)
    }

    @AuthService
    @Singleton
    @Provides
    fun provideAuthService(@AuthRetrofit retrofitAuthService: Retrofit): NanoPostApiService {
        return retrofitAuthService.create(NanoPostApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        @ApiService apiService: NanoPostApiService,
        @AuthService authService: NanoPostApiService,
        preferences: SharedPreferences,
    ): RemoteDataSource {
        return RemoteDataSource(apiService, authService, preferences)
    }

    @Singleton
    @Provides
    fun provideRemoteDataRepository(
        remoteDataSource: RemoteDataSource,
    ): RemoteDataRepository {
        return RemoteDataRepository(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideGetProfileUseCase(
        remoteDataRepository: RemoteDataRepository,
    ): GetProfileUseCase {
        return GetProfileUseCase(remoteDataRepository)
    }

    @Singleton
    @Provides
    fun provideGetProfilePostsUseCase(
        remoteDataRepository: RemoteDataRepository,
    ): GetProfilePostsUseCase {
        return GetProfilePostsUseCase(remoteDataRepository)
    }

    @Singleton
    @Provides
    fun provideGetRegisterUserUseCase(
        remoteDataRepository: RemoteDataRepository,
    ): RegisterUserUseCase {
        return RegisterUserUseCase(remoteDataRepository)
    }

    @Singleton
    @Provides
    fun provideAuthUserUseCase(
        remoteDataRepository: RemoteDataRepository,
    ): AuthUserUseCase {
        return AuthUserUseCase(remoteDataRepository)
    }

    @Singleton
    @Provides
    fun provideGetImageByUrlUseCase(
        remoteDataRepository: RemoteDataRepository,
    ): GetImageByUrlUseCase {
        return GetImageByUrlUseCase(remoteDataRepository)
    }

    @Singleton
    @Provides
    fun provideGetImageByIdUseCase(
        remoteDataRepository: RemoteDataRepository,
    ): GetImageByIdUseCase {
        return GetImageByIdUseCase(remoteDataRepository)
    }


}
