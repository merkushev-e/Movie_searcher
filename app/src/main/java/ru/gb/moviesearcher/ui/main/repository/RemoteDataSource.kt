package ru.gb.moviesearcher.ui.main.repository


import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.moviesearcher.BuildConfig
import ru.gb.moviesearcher.ui.main.model.MovieAPI
import ru.gb.moviesearcher.ui.main.model.MovieDTO
import ru.gb.moviesearcher.ui.main.model.MoviesListDTO
import java.io.IOException


class RemoteDataSource {

    private val movieAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client((createOkHttpClient(MovieApiInterceptor())))
        .build().create(MovieAPI::class.java)

    private fun createOkHttpClient(movieApiInterceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(movieApiInterceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()

    }

    inner class MovieApiInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }


    fun getMovieDetails(movieId: Int, callback: Callback<MovieDTO>) {
        movieAPI.getMovie(movieId, BuildConfig.MOVIE_DB_API_KEY).enqueue(callback)
    }

    fun getNewMovieDetails(page: Int, callback: Callback<MoviesListDTO>) {
        movieAPI.getNewMovie(BuildConfig.MOVIE_DB_API_KEY, page).enqueue(callback)
    }

    fun getPopularMovieDetails(page: Int, callback: Callback<MoviesListDTO>) {
        movieAPI.getPopularMovie(BuildConfig.MOVIE_DB_API_KEY, page).enqueue(callback)
    }


}