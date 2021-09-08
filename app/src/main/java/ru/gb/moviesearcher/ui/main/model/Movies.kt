package ru.gb.moviesearcher.ui.main.model

import ru.gb.moviesearcher.R

data class Movies(
    val movie: Movie = getDefaultMovie(),
    val rating: Double = 7.0,
    val year: Int = 2021
)

fun getDefaultMovie(): Movie =
    Movie("Good Movie", "Description of movie", 7.2, 2020, R.drawable.movie_poster)


fun getWorldCities(): List<Movie> {
    return listOf(
        Movie(
            "Зеленая миля",
            "В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
            9.1,
            1999,
            R.drawable.green_mile
        ),
        Movie(
            "Пираты Карибского моря",
            "ират нападает на армию мертвецов, чтобы вернуть свой корабль. Боевик о первых приключениях Джека Воробья",
            8.3,
            2003,
            R.drawable.pirates
        ),
        Movie(
            "Гарри Поттер и философский камень",
            "Гарри поступает в школу магии Хогвартс и заводит друзей. Первая часть большой франшизы о маленьком волшебнике",
            8.2,
            2001,
            R.drawable.movie_poster
        ),
        Movie(
            "Властелин колец: Братство Кольца",
            "В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
            8.6,
            2001,
            R.drawable.movie_poster
        ),
        Movie(
            "Легенда о волках",
            "В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
            8.2,
            2020,
            R.drawable.movie_poster
        )
    )
}


