package ru.gb.moviesearcher.ui.main.viewmodel

import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.Movies

sealed class AppState{
    data class Success(val movies: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading: AppState()
}
