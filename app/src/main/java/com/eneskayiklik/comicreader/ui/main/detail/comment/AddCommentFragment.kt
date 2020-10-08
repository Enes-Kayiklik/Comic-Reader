package com.eneskayiklik.comicreader.ui.main.detail.comment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.model.comment.Review
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_comment.*
import java.util.*

@AndroidEntryPoint
class AddCommentFragment : Fragment(R.layout.fragment_add_comment) {
    private val navArgs by navArgs<AddCommentFragmentArgs>()
    private val commentViewModel: CommentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonsOnClick()
    }

    private fun setupButtonsOnClick() {
        switchIsAnonim.setOnCheckedChangeListener { _, isChecked ->
            edtNameSurname.isEnabled = !isChecked
        }

        btnShareReview.setOnClickListener {
            checkReviewData()
        }
    }

    private fun checkReviewData() {
        val name =
            if (switchIsAnonim.isChecked) "Anonim"
            else edtNameSurname.text.toString()
        val reviewText = edtFullReview.text.toString()
        val reviewPoint = ratingReview.rating

        when {
            name.isEmpty() -> edtNameSurname.error = "Doldurulması gerek."
            reviewText.isEmpty() -> edtFullReview.error = "Doldurulması gerek."
            reviewPoint == 0F -> Toast.makeText(
                this.requireContext(),
                "Puan Vermelisiniz.",
                Toast.LENGTH_LONG
            ).show()
            else -> shareReview(
                Review(
                    author = name,
                    fullReview = reviewText,
                    rating = reviewPoint,
                    time = Timestamp(Date(System.currentTimeMillis()))
                )
            )
        }
    }

    private fun shareReview(review: Review) {
        commentViewModel.shareReview(navArgs.currentBook.docId, review)
    }
}