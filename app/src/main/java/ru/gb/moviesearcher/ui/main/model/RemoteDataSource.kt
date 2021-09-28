package ru.gb.moviesearcher.ui.main.model


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.moviesearcher.BuildConfig


class RemoteDataSource {

    private val movieAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(MovieAPI::class.java)


    fun getMovieDetails(movieId: Int, callback: Callback<MovieDTO>) {
        movieAPI.getMovie(movieId, BuildConfig.MOVIE_DB_API_KEY).enqueue(callback)
    }


//    fun getWeatherDetails(requestLink: String, callback: Callback) {
//        val builder: Request.Builder = Request.Builder().apply {
//            url(requestLink)
////            requestLink + "${id}?api_key=${BuildConfig.MOVIE_DB_API_KEY}"
//        }
//        OkHttpClient().newCall(builder.build()).enqueue(callback)
//    }
}