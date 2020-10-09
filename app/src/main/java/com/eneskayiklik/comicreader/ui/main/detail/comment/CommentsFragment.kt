package com.eneskayiklik.comicreader.ui.main.detail.comment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.utils.Functions.makeInvisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_comments.*

@AndroidEntryPoint
class CommentsFragment : Fragment(R.layout.fragment_comments) {
    private val navArgs by navArgs<CommentsFragmentArgs>()
    private val commentViewModel: CommentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonsOnClick()
        setupObserver()
    }

    private fun setupObserver() {
        commentViewModel.comments.observe(this.viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                layoutEmptyComment.makeInvisible()
                recyclerViewComments.adapter = CommentAdapter(it)
            }
        })
    }

    private fun setupButtonsOnClick() {
        btnAddComment.setOnClickListener {
            val action = CommentsFragmentDirections.actionCommentsFragmentToAddCommentFragment(
                currentBook = navArgs.currentBook
            )
            findNavController().navigate(action)
        }

        btnBack.setOnClickListener {
            this.requireActivity().onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        commentViewModel.getAllComments(navArgs.currentBook.docId)
    }
}