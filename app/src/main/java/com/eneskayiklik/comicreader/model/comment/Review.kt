package com.eneskayiklik.comicreader.model.comment

import com.google.firebase.Timestamp
import java.util.*

data class Review(
    val author: String = "",
    val fullReview: String = "",
    val rating: Float = 0F,
    val time: Timestamp = Timestamp(Date(System.currentTimeMillis()))
)