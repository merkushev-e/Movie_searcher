package ru.gb.moviesearcher.ui.main.repository

import retrofit2.Callback
import ru.gb.moviesearcher.ui.main.model.MoviesListDTO

interface Repository {

    fun getNewMoviesFromServer(page: Int, callback: Callback<MoviesListDTO>)
    fun getPopularMoviesFromServer(page: Int, callback: Callback<MoviesListDTO>)
}