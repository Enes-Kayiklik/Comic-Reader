package com.eneskayiklik.comicreader.ui.main.bookshelf

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eneskayiklik.comicreader.model.book.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookShelfViewModel @ViewModelInject constructor(
    private val bookShelfRepository: BookShelfRepository
) : ViewModel() {
    private var _allBooks = MutableLiveData<List<Book>>()
    val allBooks: LiveData<List<Book>>
        get() = _allBooks

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _allBooks.postValue(bookShelfRepository.getAllBooks())
        }
    }
}