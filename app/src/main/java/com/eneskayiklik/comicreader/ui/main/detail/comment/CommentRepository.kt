package com.eneskayiklik.comicreader.ui.main.detail.comment

import android.util.Log
import com.eneskayiklik.comicreader.model.book.Book
import com.eneskayiklik.comicreader.model.comment.Review
import com.eneskayiklik.comicreader.utils.Constants.COMMENT_COLLECTION
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val bookRef: CollectionReference
) {
    suspend fun getAllComments(bookId: String): List<Review> {
        return try {
            bookRef.document(bookId).collection(COMMENT_COLLECTION).get().await()
                .toObjects(Review::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            emptyList()
        }
    }

    suspend fun shareComment(bookId: String, review: Review) {
        try {
            bookRef.document(bookId).collection(COMMENT_COLLECTION)
                .add(review).await()
            increaseReviewCount(bookId, review)
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun increaseReviewCount(bookId: String, review: Review) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bookRef.document(bookId).get().addOnSuccessListener {
                    val book = it.toObject(Book::class.java)
                    book?.let {
                        bookRef.document(bookId).update(
                            mapOf(
                                "rating" to (book.rating + review.rating),
                                "reviewCount" to (book.reviewCount + 1)
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "${e.message}")
            }
        }
    }

    companion object {
        const val TAG = "Comment Repository"
    }
}