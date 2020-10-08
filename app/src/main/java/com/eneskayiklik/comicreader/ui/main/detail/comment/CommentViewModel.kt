package com.eneskayiklik.comicreader.ui.main.detail.comment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eneskayiklik.comicreader.model.comment.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel @ViewModelInject constructor(
    private val commentRepository: CommentRepository
) : ViewModel() {
    private var _comments = MutableLiveData<List<Review>>()
    val comments: LiveData<List<Review>>
        get() = _comments

    fun getAllComments(bookId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _comments.postValue(commentRepository.getAllComments(bookId))
        }
    }

    fun shareReview(bookId: String, review: Review) {
        CoroutineScope(Dispatchers.IO).launch {
            commentRepository.shareComment(bookId, review)
        }
    }
}