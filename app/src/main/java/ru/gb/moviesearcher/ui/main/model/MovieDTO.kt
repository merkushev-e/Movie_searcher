package ru.gb.moviesearcher.ui.main.model

data class MovieDTO(

//    val genres: Array<GenresDTO>?,
    val original_title: String?,
    val overview: String?,
    val id: Int?,
    val release_date: String?,
    val title: String?,
    val vote_average: Double?,

    ) {
        data class GenresDTO(
                val id: Int?,
                val name: String?
        )
}