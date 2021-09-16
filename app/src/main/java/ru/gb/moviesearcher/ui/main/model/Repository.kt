package ru.gb.moviesearcher.ui.main.model

interface Repository {
//    fun getNewMoviesFromLocalStorage(): List<Movie>
//    fun getPopularMoviesFromLocalStorage(): List<Movie>
    fun getNewMoviesFromServer(): List<Movie>
    fun getPopularMoviesFromServer(): List<Movie>

}