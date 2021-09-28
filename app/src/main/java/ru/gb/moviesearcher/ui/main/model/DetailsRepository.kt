package ru.gb.moviesearcher.ui.main.model

import retrofit2.Callback


interface DetailsRepository {

    fun getMovieDetailsFromServer(
        movieId: Int,
        callback: Callback<MovieDTO>
    )
}