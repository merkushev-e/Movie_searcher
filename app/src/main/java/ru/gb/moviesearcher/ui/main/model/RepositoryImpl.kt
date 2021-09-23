package ru.gb.moviesearcher.ui.main.model

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

class RepositoryImpl : Repository {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getNewMoviesFromServer(listener: MovieListLoader.MovieLoaderListener) {
        val movieListLoader = MovieListLoader(listener)
        movieListLoader.goToInternet(isNewMovies = true)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getPopularMoviesFromServe(listener: MovieListLoader.MovieLoaderListener) {
        val movieListLoader = MovieListLoader(listener)
        movieListLoader.goToInternet(isNewMovies = false)
    }


}