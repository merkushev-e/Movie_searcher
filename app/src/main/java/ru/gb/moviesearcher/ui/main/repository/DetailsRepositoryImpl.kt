package ru.gb.moviesearcher.ui.main.repository

import retrofit2.Callback
import ru.gb.moviesearcher.ui.main.model.MovieDTO


class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getMovieDetailsFromServer(movieId: Int, callback: Callback<MovieDTO>) {
        remoteDataSource.getMovieDetails(movieId, callback)
    }

}