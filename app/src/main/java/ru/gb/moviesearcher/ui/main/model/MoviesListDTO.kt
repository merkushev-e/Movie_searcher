package ru.gb.moviesearcher.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesListDTO(
    val page: Int,
    val results: List<MovieList>,
    val total_pages: Int,
): Parcelable
{
    @Parcelize
    data class MovieList(
        val id: Int = 1,
        val overview: String = "",
        val poster_path: String = "",
        val release_date: String = "",
        val title: String = "",
        val vote_average: Float = 0.0F
    ): Parcelable
}