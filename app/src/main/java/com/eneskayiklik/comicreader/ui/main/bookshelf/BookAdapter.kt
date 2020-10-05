package com.eneskayiklik.comicreader.ui.main.bookshelf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.model.book.Book
import kotlinx.android.synthetic.main.one_row_book.view.*

class BookAdapter(
    private val bookList: List<Book>
) : RecyclerView.Adapter<BookAdapter.CustomViewHolder>() {
    override fun getItemCount() = bookList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CustomViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.one_row_book, parent, false)
    )


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBook = bookList[position]
        holder.itemView.apply {
            imgBook.load(currentBook.iconUrl) {
                crossfade(true)
            }
            tvBookName.text = currentBook.name
            tvAuthorName.text = currentBook.authorName
            setOnClickListener {
                val action =
                    BookShelfFragmentDirections.actionBookShelfFragmentToBookDetailFragment(
                        currentBook
                    )
                findNavController().navigate(action)
            }
        }
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}