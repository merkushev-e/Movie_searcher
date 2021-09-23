package ru.gb.moviesearcher.ui.main.model

interface Repository {
    fun getNewMoviesFromServer(listener: MovieListLoader.MovieLoaderListener)
    fun getPopularMoviesFromServe(listener: MovieListLoader.MovieLoaderListener)
}