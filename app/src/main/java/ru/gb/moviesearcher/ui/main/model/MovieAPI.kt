package ru.gb.moviesearcher.ui.main.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("3/movie/{id}?")
    fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        ): Call<MovieDTO>


    @GET("3/movie/upcoming?")
    fun getNewMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<MoviesListDTO>

    @GET("3/movie/popular?")
    fun getPopularMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<MoviesListDTO>
}