package ru.gb.moviesearcher.ui.main.repository

import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.MovieDTO
import ru.gb.moviesearcher.ui.main.model.room.HistoryEntity

interface LocalRepository {
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
    fun updateEntity(movie: Movie)
    fun updateEntity2(movie: Movie)
    fun updateCurrentEntity(movie: Movie)
    fun getNoteFromDb(movieId: Long): String
}