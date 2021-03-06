package ru.gb.moviesearcher.ui.main.utils

import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.room.HistoryEntity
import java.util.*

fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<Movie> {
    return entityList.map { HistoryEntity ->
        Movie(
            HistoryEntity.movie_Id,
            HistoryEntity.movieName,
            HistoryEntity.releaseDate,
            HistoryEntity.rate,
            HistoryEntity.note,
        HistoryEntity.timestamp
        )
    }
}

fun convertWeatherToEntity(movie: Movie): HistoryEntity {
    return HistoryEntity(
        movie.id,
        movie.movieName,
        movie.releaseDate,
        movie.rate,
        Date().time,
        movie.note
    )
}
