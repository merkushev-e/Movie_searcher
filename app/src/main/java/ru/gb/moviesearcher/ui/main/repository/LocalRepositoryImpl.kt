package ru.gb.moviesearcher.ui.main.repository

import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.room.HistoryDao
import ru.gb.moviesearcher.ui.main.model.room.HistoryEntity
import ru.gb.moviesearcher.ui.main.utils.convertHistoryEntityToMovie
import ru.gb.moviesearcher.ui.main.utils.convertWeatherToEntity
import java.util.*

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {

    override fun getAllHistory(): List<Movie> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }


    override fun saveEntity(movie: Movie) {
        return localDataSource.insert(convertWeatherToEntity(movie))

    }




}
