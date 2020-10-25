package com.eneskayiklik.comicreader.service

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.eneskayiklik.comicreader.ui.main.read.ReadBookViewModel
import com.eneskayiklik.comicreader.ui.main.read.ReadBookViewModel.Companion.file
import com.eneskayiklik.comicreader.utils.Constants
import com.eneskayiklik.comicreader.utils.Constants.MAX_PROGRESS
import com.eneskayiklik.comicreader.utils.Constants.NOTIFICATION_ID
import com.eneskayiklik.comicreader.utils.Functions.calculateProgress
import com.eneskayiklik.comicreader.utils.Functions.calculateProgressString
import com.eneskayiklik.comicreader.utils.Variables.downloadCount
import com.eneskayiklik.comicreader.utils.Variables.downloadingItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class DownloadService : LifecycleService() {
    @Inject
    lateinit var mManager: NotificationCompat.Builder

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            setupObserver()
            setupDownload(it)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setupDownload(intent: Intent) {
        val fileName = intent.getStringExtra("file_name")
        val url = intent.getStringExtra("url")
        fileName?.let {
            downloadPdf(url, fileName)
        }
    }

    private fun setupObserver() {
        downloadingItems.observe(this, Observer { item ->
            item.forEach {
                val notification = mManager
                    .setContentTitle(it.key)
                    .setProgress(MAX_PROGRESS, it.value.calculateProgress(), false)
                    .setContentText(it.value.calculateProgressString())
                    .build()

                startForeground(NOTIFICATION_ID, notification)
            }
        })
    }

    private fun downloadPdf(url: String?, name: String) {
        val pdfPath = this.getExternalFilesDir("Comic Books")?.absolutePath
        downloadCount.postValue(downloadCount.value?.plus(1))
        CoroutineScope(Dispatchers.IO).launch {
            PRDownloader.download(url, pdfPath, "$name.pdf")
                .build()
                .setOnProgressListener { progress -> downloadingItems.postValue(mutableMapOf(name to progress)) }
                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        downloadCount.postValue(downloadCount.value?.minus(1))
                        downloadingItems.value?.remove(name)
                        val fileUri = FileProvider.getUriForFile(
                            this@DownloadService,
                            Constants.PACKAGE_NAME,
                            File("$pdfPath/$name.pdf")
                        )
                        file.postValue(fileUri)
                        if (downloadingItems.value.isNullOrEmpty())
                            stopService()
                    }

                    override fun onError(error: Error?) {
                        Log.e(ReadBookViewModel.TAG, "${error?.connectionException?.message}")
                        stopService()
                    }
                })
        }
    }

    private fun stopService() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(true)
        stopSelf()
    }
}