package com.eneskayiklik.comicreader.ui.main.read

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.builder.FileLoaderBuilder
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ReadBookViewModel @ViewModelInject constructor(
    private val fileLoader: FileLoaderBuilder
) : ViewModel() {
    private var _file = MutableLiveData<File>()
    val file: LiveData<File>
        get() = _file

    fun loadBookData(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            fileLoader
                .fromDirectory("PDF", FileLoader.DIR_CACHE)
                .load(url)
                .asFile(object : FileRequestListener<File> {
                    override fun onLoad(request: FileLoadRequest?, response: FileResponse<File>?) {
                        _file.postValue(response?.body)
                    }

                    override fun onError(request: FileLoadRequest?, t: Throwable?) {
                        Log.e(TAG, "${t?.message}")
                    }
                })
        }
    }

    companion object {
        const val TAG = "Read Book View Model"
    }
}
