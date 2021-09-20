package ru.gb.moviesearcher.ui.main.viewmodel

import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.Movies
import ru.gb.moviesearcher.ui.main.model.MoviesListDTO

sealed class AppState{
    data class Success(val movieDTO: MoviesListDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading: AppState()
}
