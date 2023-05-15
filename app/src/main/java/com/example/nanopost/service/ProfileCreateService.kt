//package com.example.nanopost.service
//
//import android.app.NotificationChannel
//import android.app.Service
//import android.content.Context
//import android.content.Intent
//import android.os.IBinder
//import androidx.core.app.NotificationChannelCompat
//import androidx.core.app.NotificationManagerCompat
//import com.example.nanopost.domain.GetImageByIdUseCase
//import com.example.nanopost.domain.GetProfileUseCase
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.*
//import javax.inject.Inject
//
//@AndroidEntryPoint
//class ProfileCreateService: Service(), CoroutineScope by MainScope() {
//
//    @Inject lateinit var getProfile: GetProfileUseCase
//
//    override fun onBind(p0: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        if(intent?.action == "$packageName$ACTION_CREATE_PROFILE"){
//            val username = intent.getStringExtra(
//                "$packageName$EXTRA_USERNAME"
//            )
//            requireNotNull(username)
//            val displayName = intent.getStringExtra(
//                "$packageName$EXTRA_DISPLAY_NAME"
//            )
//            val bio = intent.getStringExtra(
//                "$packageName$EXTRA_BIO"
//            )
//             launch {
//                 delay(10_000L)
//             }
//
//            return START_STICKY
//        }
//        return super.onStartCommand(intent, flags, startId)
//    }
//
//    private fun createNotificationChannel(){
//        val channel = NotificationChannelCompat.Builder(
//            NOTIFICATION_CHANNEL_ID,
//            NotificationManagerCompat.IMPORTANCE_LOW
//        ).build()
//        notofocationManager.createNotification
//    }
//
//    override fun onDestroy() {
//        cancel()
//        super.onDestroy()
//    }
//
//    companion object {
//
//        fun newIntent(
//            context: Context,
//            username: String
//        ): Intent()
//
//        const val NOTIFICATION_ID = 1
//
//        const val NOTIFICATION_CHANNEL_ID = "data_upload"
//
//        const val ACTION_CREATE_PROFILE = ".ACTION_CREATE_PROFILE"
//
//        const val EXTRA_USERNAME = ".EXTRA_USERNAME"
//
//        const val EXTRA_DISPLAY_NAME = ".EXTRA_DISPLAY_NAME"
//
//        const val EXTRA_BIO = ".EXTRA_BIO"
//    }
//}