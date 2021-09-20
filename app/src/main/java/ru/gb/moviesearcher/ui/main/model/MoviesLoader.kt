package ru.gb.moviesearcher.ui.main.model

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MoviesLoader(
    private val movieId: Int,
    private val listener: MovieLoaderListener

) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun goToInternet() {
        var urlConnection: HttpsURLConnection? = null
        val handler = Handler()

        Thread {
            var uri =
                URL("https://api.themoviedb.org/3/movie/${movieId}?api_key=5a9755a312233a2e4518996cadd72e16")
//            URL("https://api.themoviedb.org/3/movie/${movieId}?api_key=5a9755a312233a2e4518996cadd72e16")


            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection?.apply {
//                    requestMethod = "GET"
                    readTimeout = 10000
                }

                val reader = BufferedReader(InputStreamReader(urlConnection?.inputStream))
                val result = reader.lines().collect(Collectors.joining("\n"))
                val movieDTO: MovieDTO = Gson().fromJson(result, MovieDTO::class.java)


                handler.post{listener.onLoaded(movieDTO)}

            } catch (e: Exception){
                listener.onFailed(e)
                Log.e("","FAILED",e)
            } finally {
                urlConnection?.disconnect()
            }
        }.start()
    }


    interface MovieLoaderListener {
        fun onLoaded(movie: MovieDTO)
        fun onFailed(throwable: Throwable)
    }

}