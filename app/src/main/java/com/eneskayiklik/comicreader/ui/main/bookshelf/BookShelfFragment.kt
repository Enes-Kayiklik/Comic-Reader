package com.eneskayiklik.comicreader.ui.main.bookshelf

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.model.book.Book
import com.eneskayiklik.comicreader.utils.Functions.makeInvisible
import com.eneskayiklik.comicreader.utils.Functions.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_bookshelf.*

@AndroidEntryPoint
class BookShelfFragment : Fragment(R.layout.fragment_bookshelf) {
    private val bookShelfViewModel: BookShelfViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        if (bookShelfViewModel.allBooks.value.isNullOrEmpty())
            setupObserver()
        else
            setupRecyclerView(bookShelfViewModel.allBooks.value)
    }

    private fun setupObserver() {
        bookShelfViewModel.allBooks.observe(this.viewLifecycleOwner, Observer {
            setupRecyclerView(it)
        })
    }

    private fun setupRecyclerView(books: List<Book>?) {
        changeLayoutVisibilities()
        recyclerViewBooks.itemAnimator = SlideInLeftAnimator()
        recyclerViewBooks.adapter = BookAdapter(books ?: emptyList())
        recyclerViewBooks.layoutManager =
            GridLayoutManager(this.requireContext(), 3, GridLayoutManager.VERTICAL, false)
    }

    private fun changeLayoutVisibilities() {
        loadingView.makeInvisible()
        containerView.makeVisible()
    }
}