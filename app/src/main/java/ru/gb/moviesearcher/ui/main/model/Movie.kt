package ru.gb.moviesearcher.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.gb.moviesearcher.R
@Parcelize
data class Movie(
    val moviePoster: Int,
    val movieId: Int
) : Parcelable
