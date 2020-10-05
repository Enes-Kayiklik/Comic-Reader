package com.eneskayiklik.comicreader.utils

import android.view.View

object Functions {
    fun View.makeVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.makeInvisible() {
        this.visibility = View.GONE
    }
}