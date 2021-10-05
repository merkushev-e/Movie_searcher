package ru.gb.moviesearcher.ui.main.model.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class HistoryEntity(
    @PrimaryKey
    val movie_Id: Long,
    val movieName: String,
    val releaseDate: String,
    val rate: Double,
    val timestamp: Long,
    var note: String
)


