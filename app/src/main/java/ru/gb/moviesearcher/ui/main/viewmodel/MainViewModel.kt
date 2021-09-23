package ru.gb.moviesearcher.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.moviesearcher.ui.main.model.MovieListLoader
import ru.gb.moviesearcher.ui.main.model.MoviesListDTO
import ru.gb.moviesearcher.ui.main.model.Repository
import ru.gb.moviesearcher.ui.main.model.RepositoryImpl
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val repositoryImpl: Repository = RepositoryImpl()
    private val liveDataToObserveNewMovies: MutableLiveData<AppState> = MutableLiveData()
    private val liveDataToObservePopularMovies: MutableLiveData<AppState> = MutableLiveData()


    val liveDataNewMovies: LiveData<AppState> = liveDataToObserveNewMovies
    val liveDataPopularMovies: LiveData<AppState> = liveDataToObservePopularMovies



    fun getMovieFromInternet() = getDataFromInternet()


    private fun getDataFromInternet() {
        liveDataToObserveNewMovies.value = AppState.Loading
        liveDataToObservePopularMovies.value = AppState.Loading
        repositoryImpl.getNewMoviesFromServer(object : MovieListLoader.MovieLoaderListener{
            override fun onLoaded(movie: MoviesListDTO) {
                liveDataToObserveNewMovies.postValue(AppState.Success(movie))
            }

            override fun onFailed(throwable: Throwable) {
                liveDataToObserveNewMovies.postValue(AppState.Error(throwable))
            }

        })

        repositoryImpl.getPopularMoviesFromServe(object : MovieListLoader.MovieLoaderListener{
            override fun onLoaded(movie: MoviesListDTO) {
                liveDataToObservePopularMovies.postValue(AppState.Success(movie))
            }

            override fun onFailed(throwable: Throwable) {
                liveDataToObservePopularMovies.postValue(AppState.Error(throwable))
            }

        })
    }
}

