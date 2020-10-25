package com.eneskayiklik.comicreader.ui.main.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.ui.main.read.ReadBookViewModel
import com.eneskayiklik.comicreader.ui.main.read.ReadBookViewModel.Companion.file
import com.eneskayiklik.comicreader.utils.Functions.makeInvisible
import com.eneskayiklik.comicreader.utils.Functions.makeVisible
import com.eneskayiklik.comicreader.utils.Variables.downloadingItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_book_detail.*

@AndroidEntryPoint
class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {
    private val navArgs by navArgs<BookDetailFragmentArgs>()
    private val redBookViewModel: ReadBookViewModel by viewModels()
    private var isDownloading = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupButtonsOnClick()
        setupObserver()
    }

    private fun setupButtonsOnClick() {
        // Navigate to Read Book Fragment
        btnReadBook.setOnClickListener {
            val action = BookDetailFragmentDirections.actionBookDetailFragmentToReadBookFragment(
                navArgs.currentBook
            )
            findNavController().navigate(action)
        }

        // Download PDF from Firebase
        btnDetailDownload.setOnClickListener {
            if (!isDownloading) {
                isDownloading = !isDownloading
                progressBarDetailDownload.makeVisible()
                redBookViewModel.getBookData(
                    navArgs.currentBook.bookUrl,
                    navArgs.currentBook.name,
                    this.requireContext()
                )
            }
        }

        btnDetailSeeComments.setOnClickListener {
            val action = BookDetailFragmentDirections.actionBookDetailFragmentToCommentsFragment(
                currentBook = navArgs.currentBook
            )
            findNavController().navigate(action)
        }

        icBack.setOnClickListener {
            this.requireActivity().onBackPressed()
        }
    }

    private fun setupObserver() {
        // This method call when download on complete
        file.observe(this.viewLifecycleOwner, Observer {
            isDownloading = false
            Toast.makeText(
                this.requireContext(),
                resources.getString(R.string.downloadFolder),
                Toast.LENGTH_LONG
            ).show()
            progressBarDetailDownload.makeInvisible()
        })

        // Show user download progress
        downloadingItems.observe(this.viewLifecycleOwner, Observer { item ->
            item[navArgs.currentBook.name]?.let {
                val totalPercent = ((it.currentBytes * 100) / it.totalBytes).toInt()
                progressBarDetailDownload.progress = totalPercent
            }
        })
    }

    private fun setupData() {
        val currentBook = navArgs.currentBook
        imgBookDetail.load(currentBook.iconUrl)
        tvBookDetailAuthor.text = currentBook.authorName
        tvBookDetailName.text = currentBook.name
        ratingBar.rating = currentBook.rating / currentBook.reviewCount
        tvDetailDesc.text = currentBook.desc
        tvBookDetailReviewCount.text = "${currentBook.reviewCount}".plus(" İnceleme")
        tvBookDetailPageCount.text = "${currentBook.pageCount}".plus(" Sayfa")
    }

    override fun onStart() {
        super.onStart()
        // If selected item currently downloading  than show download progress bar to user
        downloadingItems.value?.get(navArgs.currentBook.name)?.let {
            progressBarDetailDownload.makeVisible()
        }
    }
}