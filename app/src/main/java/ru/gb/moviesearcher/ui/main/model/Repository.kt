package ru.gb.moviesearcher.ui.main.model

interface Repository {
    fun getMoviesFromServer(): List<Movie>
    fun getMoviesFromLocalStorage(): List<Movie>
}