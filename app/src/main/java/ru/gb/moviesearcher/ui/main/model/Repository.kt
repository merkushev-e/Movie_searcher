package ru.gb.moviesearcher.ui.main.model

interface Repository {
    fun getMoviesFromServer(): Movies
    fun getMoviesFromLocalStorage(): Movies
}