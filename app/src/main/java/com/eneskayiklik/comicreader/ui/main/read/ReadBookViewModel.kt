package com.eneskayiklik.comicreader.ui.main.read

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.Progress
import com.eneskayiklik.comicreader.utils.Variables.downloadCount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ReadBookViewModel @ViewModelInject constructor(
    private val pdfPath: String
) : ViewModel() {
    private var _file = MutableLiveData<Uri>()
    val file: LiveData<Uri>
        get() = _file

    private var _progress = MutableLiveData<Progress>()
    val progress: LiveData<Progress>
        get() = _progress

    fun getBookData(url: String, name: String, context: Context) {
        val file = File("$pdfPath/$name.pdf")
        if (!file.exists()) {
            downloadPdf(url, name, context)
        } else {
            _file.postValue(file.toUri())
        }
    }

    private fun downloadPdf(url: String, name: String, context: Context) {
        downloadCount.postValue(downloadCount.value?.plus(1))
        CoroutineScope(Dispatchers.IO).launch {
            PRDownloader.download(url, pdfPath, "$name.pdf")
                .build().setOnProgressListener { progress -> _progress.postValue(progress) }
                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        downloadCount.postValue(downloadCount.value?.minus(1))
                        val fileUri = FileProvider.getUriForFile(
                            context,
                            "com.eneskayiklik.comicreader",
                            File("$pdfPath/$name.pdf")
                        )
                        _file.postValue(fileUri)
                    }

                    override fun onError(error: Error?) {
                        Log.e(TAG, "${error?.connectionException?.message}")
                    }
                })
        }
    }

    companion object {
        const val TAG = "Read Book View Model"
    }
}
