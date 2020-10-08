package com.eneskayiklik.comicreader.ui.main.detail.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.model.comment.Review
import kotlinx.android.synthetic.main.one_row_comment.view.*
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter(
    private val commentList: List<Review>
) : RecyclerView.Adapter<CommentAdapter.CustomViewHolder>() {
    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.one_row_comment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentComment = commentList[position]
        holder.itemView.apply {
            tvAuthor.text = "Kimden: ${currentComment.author}"
            tvLongReview.text = currentComment.fullReview
            ratingBarReview.rating = currentComment.rating
            tvTimestamp.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(currentComment.time.toDate())
        }
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}