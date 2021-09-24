package ru.gb.moviesearcher.ui.main.model

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val MOVIE_ID_EXTRA = "MOVIE ID"
const val DEFAULT_VALUE = 550
const val MOVIE_DTO_EXTRA = "Movie DTO"
const val ERROR_EMPTY_DATA_RESULT = "Movie DTO"
const val DETAILS_URL_MALFORMED_EXTRA = "DETAILS URL MALFORMED"
const val DETAILS_DATA_EMPTY_EXTRA = "DETAILS DATA_EMPTY MALFORMED"

    const val RESULT_EXTRA = "RESULT EXTRA"
const val SUCCESS_RESULT = "Success_RESULT"


class DetailsService(name: String = "DetailFragment") : IntentService(name) {

//    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        Log.d("Debug", "Service started ${Thread.currentThread()}")

        intent?.let { intent ->
            val movieId: Int = intent.getIntExtra(MOVIE_ID_EXTRA, DEFAULT_VALUE)

            if (movieId == DEFAULT_VALUE) {
                onEmptyData()
            } else {
                loadMovie(movieId)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie(movieId: Int) {

        var urlConnection: HttpsURLConnection? = null
        val uri = try {
            URL("https://api.themoviedb.org/3/movie/${movieId}?api_key=5a9755a312233a2e4518996cadd72e16")

        } catch (e: MalformedURLException) {
            onMalformedURL()
            return
        }

        try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.apply {
                readTimeout = 10000
            }

            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = reader.lines().collect(Collectors.joining("\n"))
            val movieDTO: MovieDTO = Gson().fromJson(result, MovieDTO::class.java)


            onResponse(movieDTO)

        } catch (e: Exception) {
            onErrorResponse(e.message ?: "Unknown error")
            e.printStackTrace()
            Log.e("", "FAILED", e)
        } finally {
            urlConnection?.disconnect()
        }
    }

    private fun onErrorResponse(s: String) {
        TODO("Not yet implemented")
    }

    private fun onResponse(movieDTO: MovieDTO) {
        onSuccessResponse(movieDTO)
    }

    private fun onEmptyResponse() {
        Log.d("Debug", "Empty Response")
        LocalBroadcastManager.getInstance(this).sendBroadcast(
            Intent(DETAILS_INTENT_FILTER).putExtra(RESULT_EXTRA, ERROR_EMPTY_DATA_RESULT)
        )
    }

    private fun onSuccessResponse(movieDTO: MovieDTO) {
        Log.d("Debug", "Send Broadcast ${Thread.currentThread()}")
        LocalBroadcastManager.getInstance(this)
            .sendBroadcast(
                Intent(DETAILS_INTENT_FILTER)
                    .putExtra(RESULT_EXTRA, SUCCESS_RESULT)
                    .putExtra(MOVIE_DTO_EXTRA, movieDTO)
            )
    }

    private fun onMalformedURL() {
        Log.d("Debug", "URL problem ${Thread.currentThread()}")
        LocalBroadcastManager.getInstance(this)
            .sendBroadcast(
                Intent(DETAILS_INTENT_FILTER)
                    .putExtra(RESULT_EXTRA, SUCCESS_RESULT)
                    .putExtra(MOVIE_DTO_EXTRA, DETAILS_URL_MALFORMED_EXTRA)
            )
    }

    private fun onEmptyData() {
        Log.d("Debug", "Empty id problem ${Thread.currentThread()}")
        LocalBroadcastManager.getInstance(this)
            .sendBroadcast(
                Intent(DETAILS_INTENT_FILTER).putExtra(RESULT_EXTRA, SUCCESS_RESULT)
                    .putExtra(MOVIE_DTO_EXTRA,DETAILS_DATA_EMPTY_EXTRA )
            )
    }
}
