package ru.gb.moviesearcher.ui.main.repository

import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.room.HistoryDao
import ru.gb.moviesearcher.ui.main.model.room.HistoryEntity
import ru.gb.moviesearcher.ui.main.utils.convertHistoryEntityToMovie
import ru.gb.moviesearcher.ui.main.utils.convertWeatherToEntity
import java.util.*

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {

   private lateinit var historyEntity: HistoryEntity
    override fun getAllHistory(): List<Movie> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }


    override fun saveEntity(movie: Movie) {
        historyEntity = HistoryEntity(
            0,
            movie.movieName,
            movie.releaseDate,
            movie.rate,
            Date().time,
            movie.note)

        return localDataSource.insert((historyEntity))

    }

    override fun updateEntity(movie: Movie) {
        if (historyEntity == null){
            historyEntity = HistoryEntity(
                0,
                movie.movieName,
                movie.releaseDate,
                movie.rate,
                Date().time,
                movie.note)
        } else{
            historyEntity.note = movie.note
        }

        return localDataSource.update(historyEntity)
    }


}
