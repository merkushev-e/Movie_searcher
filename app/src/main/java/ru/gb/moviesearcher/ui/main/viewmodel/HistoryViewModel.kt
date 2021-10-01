package ru.gb.moviesearcher.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.moviesearcher.ui.main.model.app.App
import ru.gb.moviesearcher.ui.main.repository.LocalRepository
import ru.gb.moviesearcher.ui.main.repository.LocalRepositoryImpl

class HistoryViewModel(): ViewModel(){
    private val historyLiveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val historyRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

    val historyLiveData: LiveData<AppState> = historyLiveDataToObserve


    fun getAllHistory(){

        historyLiveDataToObserve.value = AppState.Loading
        historyLiveDataToObserve.value = AppState.SuccessHistory(historyRepository.getAllHistory())
    }
}