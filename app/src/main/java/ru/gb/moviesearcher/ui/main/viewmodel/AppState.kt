package ru.gb.moviesearcher.ui.main.viewmodel

import ru.gb.moviesearcher.ui.main.model.MovieDTO
import ru.gb.moviesearcher.ui.main.model.MoviesListDTO

sealed class AppState{
    data class Success(val movieDTO: MoviesListDTO) : AppState()
    data class SuccessDetails(val movieDTO: MovieDTO): AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading: AppState()
}
