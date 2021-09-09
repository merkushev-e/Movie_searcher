package ru.gb.moviesearcher.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.moviesearcher.ui.main.model.Repository
import ru.gb.moviesearcher.ui.main.model.RepositoryImpl
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val repositoryImpl: Repository = RepositoryImpl()
    private val liveDataToObserveNewMovies: MutableLiveData<AppState> = MutableLiveData()
    private val liveDataToObservePopularMovies: MutableLiveData<AppState> = MutableLiveData()


    val liveDataNewMovies: LiveData<AppState> = liveDataToObserveNewMovies
    val liveDataPopularMovies: LiveData<AppState> = liveDataToObservePopularMovies

    fun getMovieFromLocalSource() = getDataFromLocalSource()


    private fun getDataFromLocalSource() {
        liveDataToObserveNewMovies.value = AppState.Loading
        liveDataToObservePopularMovies.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserveNewMovies.postValue(AppState.Success(repositoryImpl.getNewMoviesFromLocalStorage()))
            liveDataToObservePopularMovies.postValue(AppState.Success(repositoryImpl.getPopularMoviesFromLocalStorage()))
        }.start()

    }
}

