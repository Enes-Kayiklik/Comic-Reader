package com.eneskayiklik.comicreader.ui.main.bookshelf

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
    private val bookShelfViewModel: BookShelfViewModel by activityViewModels()
    private lateinit var adapter: BookAdapter
    private lateinit var bookList: List<Book>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUtils()
        setupSearch()
    }

    private fun setupSearch() {
        edtSearchView.doOnTextChanged { text, _, _, _ ->
            if (this::bookList.isInitialized) {
                adapter.bookList = bookList.filter { it.name.contains(text ?: "", true) }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupUtils() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbarBookshelfFragment)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        if (bookShelfViewModel.allBooks.value.isNullOrEmpty())
            setupObserver().also { changeLayoutVisibilities(true) }
        else
            setupRecyclerView(bookShelfViewModel.allBooks.value)
    }

    private fun setupObserver() {
        bookShelfViewModel.allBooks.observe(this.viewLifecycleOwner, Observer {
            setupRecyclerView(it)
            changeLayoutVisibilities(false)
        })
    }

    private fun setupRecyclerView(books: List<Book>?) {
        bookList = books ?: emptyList()
        adapter = BookAdapter(bookList)
        recyclerViewBooks.itemAnimator = SlideInLeftAnimator()
        recyclerViewBooks.adapter = adapter
        recyclerViewBooks.layoutManager =
            GridLayoutManager(this.requireContext(), 3, GridLayoutManager.VERTICAL, false)
    }

    private fun changeLayoutVisibilities(isVisible: Boolean) {
        if (isVisible) {
            loadingView.makeVisible()
            recyclerViewBooks.makeInvisible()
        } else {
            loadingView.makeInvisible()
            recyclerViewBooks.makeVisible()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bookshelf_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.orderByReview -> orderByReview()
        }
        return true
    }

    private fun orderByReview() {
        if (this::bookList.isInitialized) {
            adapter.bookList = bookList.sortedByDescending { it.rating }
            adapter.notifyDataSetChanged()
        }
    }
}