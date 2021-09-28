package ru.gb.moviesearcher.ui.main.model

import retrofit2.Callback

interface Repository {

    fun getNewMoviesFromServer(page: Int, callback: Callback<MoviesListDTO>)
    fun getPopularMoviesFromServer(page: Int, callback: Callback<MoviesListDTO>)
}