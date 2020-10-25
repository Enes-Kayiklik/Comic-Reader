package com.eneskayiklik.comicreader

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.eneskayiklik.comicreader.utils.Constants.CHANNEL_ID
import com.eneskayiklik.comicreader.utils.Constants.CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initPRDownloader()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
            val mNotifyManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotifyManager.createNotificationChannel(channel)
        }
    }

    private fun initPRDownloader() {
        val config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .build()
        PRDownloader.initialize(this, config)
    }
}