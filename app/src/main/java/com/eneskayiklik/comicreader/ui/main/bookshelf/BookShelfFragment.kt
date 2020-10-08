package com.eneskayiklik.comicreader.ui.main.bookshelf

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
class BookShelfFragment : Fragment(R.layout.fragment_bookshelf), SearchView.OnQueryTextListener {
    private val bookShelfViewModel: BookShelfViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    private lateinit var bookList: List<Book>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbarBookshelfFragment)
        setHasOptionsMenu(true)

    }

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
        bookList = books ?: emptyList()
        adapter = BookAdapter(bookList)
        recyclerViewBooks.itemAnimator = SlideInLeftAnimator()
        recyclerViewBooks.adapter = adapter
        recyclerViewBooks.layoutManager =
            GridLayoutManager(this.requireContext(), 3, GridLayoutManager.VERTICAL, false)
    }

    private fun changeLayoutVisibilities() {
        loadingView.makeInvisible()
        recyclerViewBooks.makeVisible()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(text: String): Boolean {
        if (this::bookList.isInitialized) {
            val selectedBooks = bookList.filter { it.name.contains(text, true) }
            adapter.bookList = selectedBooks
            adapter.notifyDataSetChanged()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }
}