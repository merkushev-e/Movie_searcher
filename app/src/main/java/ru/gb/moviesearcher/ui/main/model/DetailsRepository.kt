package ru.gb.moviesearcher.ui.main.model

import okhttp3.Callback

interface DetailsRepository {

    fun getMovieDetailsFromServer(requestLink: String, callback: Callback)

}