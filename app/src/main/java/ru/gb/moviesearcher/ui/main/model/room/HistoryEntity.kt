package ru.gb.moviesearcher.ui.main.model.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class HistoryEntity(
    @PrimaryKey
    var movie_Id: Long,
    var movieName: String,
    var releaseDate: String,
    var rate: Double,
    var timestamp: Long,
    var note: String
)


