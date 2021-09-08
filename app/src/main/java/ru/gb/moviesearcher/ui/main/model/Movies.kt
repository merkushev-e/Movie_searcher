package ru.gb.moviesearcher.ui.main.model

data class Movies(
    val movie: Movie = getDefaultMovie(),
    val rating: Double = 7.0,
    val year: Int = 2021
)

fun getDefaultMovie(): Movie = Movie("Good Movie","Description of movie", 7.2)
