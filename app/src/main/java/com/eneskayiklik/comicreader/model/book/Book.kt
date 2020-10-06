package com.eneskayiklik.comicreader.model.book

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    val bookUrl: String = "",
    val iconUrl: String = "",
    val name: String = "",
    val desc: String = "",
    val authorName: String = "",
    val pageCount: Int = 0,
    val rating: Float = 0F
) : Parcelable