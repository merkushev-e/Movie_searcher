package ru.gb.moviesearcher.ui.main.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieListLoader(
    private val listener: MovieLoaderListener,
) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun goToInternet(isNewMovies: Boolean) {
        var urlConnection: HttpsURLConnection? = null
        val handler = Handler(Looper.getMainLooper())
        Thread {

            urlConnection = if (isNewMovies) {
                val uri =
                    URL("https://api.themoviedb.org/3/movie/upcoming?api_key=5a9755a312233a2e4518996cadd72e16&language=en-US&page=1")
                uri.openConnection() as HttpsURLConnection
            } else {
                val uri =
                    URL("https://api.themoviedb.org/3/movie/popular?api_key=5a9755a312233a2e4518996cadd72e16&language=en-US&page=1")
                uri.openConnection() as HttpsURLConnection
            }

            try {
                urlConnection?.apply {
                    readTimeout = 10000
                }

                val reader = BufferedReader(InputStreamReader(urlConnection?.inputStream))
                val result = reader.lines().collect(Collectors.joining("\n"))
                val movieListDTO: MoviesListDTO = Gson().fromJson(result, MoviesListDTO::class.java)


                handler.post { listener.onLoaded(movieListDTO) }

            } catch (e: Exception) {
                listener.onFailed(e)
                Log.e("", "FAILED", e)
            } finally {
                urlConnection?.disconnect()
            }
        }.start()
    }


    interface MovieLoaderListener {
        fun onLoaded(movie: MoviesListDTO)
        fun onFailed(throwable: Throwable)
    }

}