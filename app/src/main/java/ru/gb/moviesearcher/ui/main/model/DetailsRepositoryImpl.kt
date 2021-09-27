package ru.gb.moviesearcher.ui.main.model

import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getMovieDetailsFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getWeatherDetails(requestLink, callback)
    }

}