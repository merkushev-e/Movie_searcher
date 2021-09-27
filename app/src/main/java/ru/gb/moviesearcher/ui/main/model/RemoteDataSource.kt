package ru.gb.moviesearcher.ui.main.model

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gb.moviesearcher.BuildConfig


class RemoteDataSource {

    fun getWeatherDetails(requestLink: String, callback: Callback) {
        val builder: Request.Builder = Request.Builder().apply {
            url(requestLink)
//            requestLink + "${id}?api_key=${BuildConfig.MOVIE_DB_API_KEY}"
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}