package ru.gb.moviesearcher.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.gb.moviesearcher.R
@Parcelize
data class Movie(
    val movieName: String,
    val movieDescription: String,
    val movieRate: Double,
    val movieYear: Int,
    val moviePoster: Int
) : Parcelable
