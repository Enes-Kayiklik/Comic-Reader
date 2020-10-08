package com.eneskayiklik.comicreader.utils

import androidx.lifecycle.MutableLiveData
import com.downloader.Progress

object Variables {
    var downloadCount = MutableLiveData(0)
    var downloadingItems = MutableLiveData<MutableMap<String, Progress>>()
}