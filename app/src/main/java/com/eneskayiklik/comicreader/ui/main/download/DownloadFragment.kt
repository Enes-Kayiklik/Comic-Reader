package com.eneskayiklik.comicreader.ui.main.download

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.utils.Variables

class DownloadFragment : Fragment(R.layout.fragment_download) {

    private fun setupObserver() {
        Variables.downloadingItems.observe(this.viewLifecycleOwner, Observer {
            it.forEach { current ->
                Log.e(TAG, "${current.key} --> ${current.value.currentBytes}")
            }
        })
    }

    companion object {
        const val TAG = "Download Fragment"
    }
}