package ru.gb.moviesearcher.ui.main.repository

import retrofit2.Callback
import ru.gb.moviesearcher.ui.main.model.MoviesListDTO

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun getNewMoviesFromServer(page: Int, callback: Callback<MoviesListDTO>) {
        remoteDataSource.getNewMovieDetails(page, callback)
    }

    override fun getPopularMoviesFromServer(page: Int, callback: Callback<MoviesListDTO>) {
        remoteDataSource.getPopularMovieDetails(page,callback)
    }

}