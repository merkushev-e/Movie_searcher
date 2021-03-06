package ru.gb.moviesearcher.ui.main.model

import java.sql.Timestamp

data class Movie (
    val id: Long,
    val movieName: String,
    val releaseDate: String,
    val rate: Double,
    var note: String,
    val timestamp: Long
)