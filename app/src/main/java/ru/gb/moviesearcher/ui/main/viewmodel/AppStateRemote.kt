package ru.gb.moviesearcher.ui.main.viewmodel

import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.MovieDB

sealed class AppStateRemote{
    data class Success(val movies: List<MovieDB>) : AppStateRemote()
    data class Error(val error: Throwable) : AppStateRemote()
    object Loading: AppStateRemote()
}
