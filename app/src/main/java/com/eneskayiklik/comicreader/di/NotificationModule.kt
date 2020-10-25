package com.eneskayiklik.comicreader.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.ui.MainActivity
import com.eneskayiklik.comicreader.utils.Constants.CHANNEL_ID
import com.eneskayiklik.comicreader.utils.Constants.PENDING_CODE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {
    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder = NotificationCompat.Builder(
        context,
        CHANNEL_ID
    ).setSmallIcon(R.drawable.ic_download)
        .setAutoCancel(false)
        .setOngoing(false)
        .setContentTitle("Book Name")
        .setContentIntent(pendingIntent)

    @ServiceScoped
    @Provides
    fun providePendingIntent(
        @ApplicationContext context: Context,
        intent: Intent
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            PENDING_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    @ServiceScoped
    @Provides
    fun provideIntent(
        @ApplicationContext context: Context
    ): Intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }
}