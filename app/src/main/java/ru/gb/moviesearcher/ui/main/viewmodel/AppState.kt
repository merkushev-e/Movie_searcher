package ru.gb.moviesearcher.ui.main.viewmodel

import ru.gb.moviesearcher.ui.main.model.Movies

sealed class AppState{
    data class Success(val movies: Movies) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading: AppState()
}
