package ru.gb.moviesearcher.ui.main.model

import retrofit2.Callback


class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getMovieDetailsFromServer(movieId: Int, callback: Callback<MovieDTO>) {
        remoteDataSource.getMovieDetails(movieId, callback)
    }

}