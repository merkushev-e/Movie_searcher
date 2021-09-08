package ru.gb.moviesearcher.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.moviesearcher.ui.main.model.Repository
import ru.gb.moviesearcher.ui.main.model.RepositoryImpl
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val repositoryImpl: Repository = RepositoryImpl()
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()


    val liveData: LiveData<AppState> = liveDataToObserve

    fun getMovieFromLocalSource() = getDataFromLocalSource()


    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1500)
                liveDataToObserve.postValue(AppState.Success(repositoryImpl.getMoviesFromLocalStorage()))
        }.start()

    }
}

