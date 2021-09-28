package ru.gb.moviesearcher.ui.main.repository

import retrofit2.Callback
import ru.gb.moviesearcher.ui.main.model.MovieDTO


interface DetailsRepository {

    fun getMovieDetailsFromServer(
        movieId: Int,
        callback: Callback<MovieDTO>
    )
}