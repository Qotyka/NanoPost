package com.example.nanopost

import android.app.Application
import android.content.Intent
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
//import com.example.nanopost.service.ProfileCreateService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@HiltAndroidApp
class NanoPostApp: Application() {
}