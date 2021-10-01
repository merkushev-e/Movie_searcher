package ru.gb.moviesearcher.ui.main.model

import android.os.Parcelable
import android.provider.MediaStore
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDTO(
    val genres: List<GenresDTO>,
    val overview: String,
    val poster_path: String,
    val id: Int,
    val release_date: String,
    val title: String,
    val vote_average: Double,
) : Parcelable {
    @Parcelize
    data class GenresDTO(
        val id: Int,
        val name: String
    ): Parcelable
}