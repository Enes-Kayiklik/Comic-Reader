package com.eneskayiklik.comicreader.utils

import android.view.View
import com.downloader.Progress

object Functions {
    fun View.makeVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.makeInvisible() {
        this.visibility = View.GONE
    }

    fun Progress.calculateProgress() =
        ((this.currentBytes * 100) / this.totalBytes).toInt()

    fun Progress.calculateProgressString(): String {
        val currentByte = (this.currentBytes / 1_000_000).toInt()
        val totalByte = (this.totalBytes / 1_000_000).toInt()
        return "$currentByte".plus(" mb / $totalByte mb")
    }
}