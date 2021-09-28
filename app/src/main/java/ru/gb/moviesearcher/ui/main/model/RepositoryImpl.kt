package ru.gb.moviesearcher.ui.main.model

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun getNewMoviesFromServer(page: Int, callback: Callback<MoviesListDTO>) {
        remoteDataSource.getNewMovieDetails(page, callback)
    }

    override fun getPopularMoviesFromServer(page: Int, callback: Callback<MoviesListDTO>) {
        remoteDataSource.getPopularMovieDetails(page,callback)
    }

}