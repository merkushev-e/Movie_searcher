package ru.gb.moviesearcher.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import ru.gb.moviesearcher.BuildConfig
import ru.gb.moviesearcher.ui.main.model.*

import java.io.IOException


private const val SERVER_ERROR = "SERVER ERROR"
private const val REQUEST_ERROR = "REQUEST ERROR"
private const val CORRUPTED_DATA = "CORRUPTED DATA"
const val MAIN_LINK = "https://api.themoviedb.org/3/movie/"

class DetailsViewModel() : ViewModel() {
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())

    val liveData: LiveData<AppState> = detailsLiveData

    fun getMovieFromRemoteSource(movieDTO: MoviesListDTO.MovieList) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(MAIN_LINK + movieDTO.id+"?api_key="+ BuildConfig.MOVIE_DB_API_KEY, callBack)
    }

    private val callBack = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            detailsLiveData.postValue(AppState.Error(Throwable(e.message ?: REQUEST_ERROR)))
        }

        override fun onResponse(call: Call, response: Response) {
            val serverResponse: String? = response.body()?.string()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )

        }

    }

    private fun checkResponse(serverResponse: String): AppState {
        val movieDTO: MovieDTO = Gson().fromJson(serverResponse, MovieDTO::class.java)
        return if (movieDTO.id == null || movieDTO.overview == null || movieDTO.release_date == null
            || movieDTO.title == null || movieDTO.vote_average == null){
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else{
            AppState.SuccessDetails(movieDTO)
        }
    }


}