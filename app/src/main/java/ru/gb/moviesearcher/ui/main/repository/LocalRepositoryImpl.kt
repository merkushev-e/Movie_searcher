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
            movie.id,
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
                movie.id,
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

     override fun updateEntity2(movie: Movie) {
        historyEntity = HistoryEntity(
             movie.id,
             movie.movieName,
             movie.releaseDate,
             movie.rate,
             Date().time,
             getNoteFromDb(movie.id)

         )
//         historyEntity.movie_Id = movie.id
//         historyEntity.movieName =
//         historyEntity.releaseDate =
//         historyEntity.rate =
//         historyEntity.timestamp =
//         historyEntity.note = getNoteFromDb(movie.id)

        return localDataSource.update(historyEntity)

    }

    override fun updateCurrentEntity(movie: Movie) {
        return localDataSource.updateCurrent(movie.note,movie.id,)
    }

    override fun getNoteFromDb(movieId: Long): String {
        val historyEntity = localDataSource.getDataByMovieId(movieId)
        return historyEntity.note
    }


}
