package ru.gb.moviesearcher.ui.main.model

import ru.gb.moviesearcher.R

data class Movie(
    val movieName: String,
    val movieDescription: String,
    val movieRate: Double,
    val movieYear: Int,
    val moviePoster: Int
)
