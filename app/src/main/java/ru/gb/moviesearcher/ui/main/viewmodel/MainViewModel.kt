package ru.gb.moviesearcher.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.moviesearcher.ui.main.model.*
import ru.gb.moviesearcher.ui.main.repository.RemoteDataSource
import ru.gb.moviesearcher.ui.main.repository.Repository
import ru.gb.moviesearcher.ui.main.repository.RepositoryImpl


private const val SERVER_ERROR = "SERVER ERROR"
private const val REQUEST_ERROR = "REQUEST ERROR"


class MainViewModel : ViewModel() {

    private val repositoryImpl: Repository = RepositoryImpl(RemoteDataSource())
    private val liveDataToObserveNewMovies: MutableLiveData<AppState> = MutableLiveData()
    private val liveDataToObservePopularMovies: MutableLiveData<AppState> = MutableLiveData()


    val liveDataNewMovies: LiveData<AppState> = liveDataToObserveNewMovies
    val liveDataPopularMovies: LiveData<AppState> = liveDataToObservePopularMovies


    fun getMovieFromInternet(page: Int) = getMovieFromRemoteSource(page)


    private fun getMovieFromRemoteSource(page: Int) {
        liveDataToObserveNewMovies.value = AppState.Loading
        repositoryImpl.getNewMoviesFromServer(page, callBackNew)
        liveDataToObservePopularMovies.value = AppState.Loading
        repositoryImpl.getPopularMoviesFromServer(page, callBackPopular)
    }


    private val callBackNew = object : Callback<MoviesListDTO> {

        override fun onResponse(call: Call<MoviesListDTO>, response: Response<MoviesListDTO>) {
            val serverResponse: MoviesListDTO? = response.body()
            liveDataToObserveNewMovies.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MoviesListDTO>, t: Throwable) {
            liveDataToObserveNewMovies.postValue(
                AppState.Error(Throwable(t.message ?: REQUEST_ERROR))
            )
        }
    }

        private val callBackPopular = object : Callback<MoviesListDTO> {

            override fun onResponse(call: Call<MoviesListDTO>, response: Response<MoviesListDTO>) {
                val serverResponse: MoviesListDTO? = response.body()
                liveDataToObservePopularMovies.postValue(
                    if (response.isSuccessful && serverResponse != null) {
                        checkResponse(serverResponse)
                    } else {
                        AppState.Error(Throwable(SERVER_ERROR))
                    }
                )
            }

            override fun onFailure(call: Call<MoviesListDTO>, t: Throwable) {
                liveDataToObservePopularMovies.postValue(
                    AppState.Error(
                        Throwable(
                            t.message ?: REQUEST_ERROR))
                        )
            }
        }

        private fun checkResponse(serverResponse: MoviesListDTO): AppState {
            val moviesListDTO: MoviesListDTO = serverResponse
            return AppState.Success(moviesListDTO)
        }

    }

