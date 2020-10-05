package com.eneskayiklik.comicreader.ui.main.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.eneskayiklik.comicreader.R
import kotlinx.android.synthetic.main.fragment_book_detail.*

class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {
    private val navArgs by navArgs<BookDetailFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupButtonsOnClick()
    }

    private fun setupButtonsOnClick() {
        btnReadBook.setOnClickListener {
            val action = BookDetailFragmentDirections.actionBookDetailFragmentToReadBookFragment(
                navArgs.currentBook
            )
            findNavController().navigate(action)
        }
    }

    private fun setupData() {
        val currentBook = navArgs.currentBook
        imgBookDetail.load(currentBook.iconUrl)
        tvBookDetailAuthor.text = currentBook.authorName
        tvBookDetailName.text = currentBook.name
    }
}