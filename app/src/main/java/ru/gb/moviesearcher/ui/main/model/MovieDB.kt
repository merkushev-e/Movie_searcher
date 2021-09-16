package ru.gb.moviesearcher.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDB(
    private val movieId: Int): Parcelable
