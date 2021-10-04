package ru.gb.moviesearcher.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import ru.gb.moviesearcher.ui.main.model.*
import ru.gb.moviesearcher.ui.main.model.app.App
import ru.gb.moviesearcher.ui.main.model.room.HistoryEntity
import ru.gb.moviesearcher.ui.main.repository.*
import java.util.*


private const val SERVER_ERROR = "SERVER ERROR"
private const val REQUEST_ERROR = "REQUEST ERROR"
private const val CORRUPTED_DATA = "CORRUPTED DATA"

class DetailsViewModel() : ViewModel() {
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
    private val historyRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

    val liveData: LiveData<AppState> = detailsLiveData

    fun getMovieFromRemoteSource(movieDTO: MoviesListDTO.MovieList) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(movieDTO.id, callBack)
    }


    private val callBack = object : Callback<MovieDTO> {
        override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
            val serverResponse: MovieDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )

        }

    }

    private fun checkResponse(serverResponse: MovieDTO): AppState {
        val movieDTO: MovieDTO = serverResponse
        return if (movieDTO.id == null || movieDTO.overview == null || movieDTO.release_date == null
            || movieDTO.title == null || movieDTO.vote_average == null){
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else{
            AppState.SuccessDetails(movieDTO)
        }
    }

     fun saveMovieToDB(movie: Movie){
        historyRepository.saveEntity(movie)
    }
    fun updateInDB(movie: Movie){
        historyRepository.updateEntity(movie)
    }



}