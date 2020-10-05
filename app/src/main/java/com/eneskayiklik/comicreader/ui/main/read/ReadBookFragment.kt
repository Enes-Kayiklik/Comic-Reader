package com.eneskayiklik.comicreader.ui.main.read

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.utils.Functions.makeInvisible
import com.eneskayiklik.comicreader.utils.Functions.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_read_book.*

@AndroidEntryPoint
class ReadBookFragment : Fragment(R.layout.fragment_read_book) {
    private val navArgs by navArgs<ReadBookFragmentArgs>()
    private val redBookViewModel: ReadBookViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        redBookViewModel.file.observe(this.viewLifecycleOwner, Observer {
            changeLayoutVisibilities()
            pdfView.fromFile(it)
                .pageFling(true)
                .swipeHorizontal(true)
                .pageSnap(true)
                .load()
        })
    }

    private fun changeLayoutVisibilities() {
        loadingViewRead.makeInvisible()
        containerViewRead.makeVisible()
    }

    override fun onStart() {
        super.onStart()
        redBookViewModel.loadBookData(navArgs.currentBook.bookUrl)
    }
}