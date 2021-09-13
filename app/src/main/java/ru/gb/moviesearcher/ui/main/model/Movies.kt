package ru.gb.moviesearcher.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.gb.moviesearcher.R

@Parcelize
data class Movies(
    val movie: Movie = getDefaultMovie(),
    val rating: Double = 7.0,
    val year: Int = 2021
) : Parcelable

fun getDefaultMovie(): Movie =
    Movie("Good Movie", "Description of movie", 7.2, 2020, R.drawable.movie_poster)


fun getNewMovies(): List<Movie>  = listOf(
        Movie(
            "Гнев человеческий",
            "Хмурый мужчина прикидывается инкассатором, чтобы выйти на грабителей. Гай Ричи и Джейсон Стэйтем снова вместе",
            7.7,
            2021,
            R.drawable.gnev
        ),
        Movie(
            "Лига справедливости Зака Снайдера",
            "Бэтмен собирает команду супергероев, чтобы спасти Землю. Режиссерская версия, которую так ждали фанаты",
            7.9,
            2021,
            R.drawable.liga
        ),
        Movie(
            "Райя и последний дракон",
            "Гарри поступает в школу магии Хогвартс и заводит друзей. Первая часть большой франшизы о маленьком волшебнике",
            8.3,
            2021,
            R.drawable.raya
        ),
        Movie(
            "Властелин колец: Братство Кольца",
            "В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
            8.6,
            2021,
            R.drawable.lotr
        ),
        Movie(
            "Легенда о волках",
            "В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
            8.2,
            2021,
            R.drawable.movie_poster
        )
    )

fun getPopularMovies(): List<Movie> = listOf(
        Movie(
            "Зеленая миля",
            "В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
            9.1,
            1999,
            R.drawable.green_mile
        ),
        Movie(
            "Властелин колец: Возвращение короля",
            "Арагорн штурмует Мордор, а Фродо устал бороться с чарами кольца. Эффектный финал саги, собравший 11 «Оскаров»",
            8.6,
            2003,
            R.drawable.lotr
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
            R.drawable.harry
        ),

        Movie(
            "Легенда о волках",
            "В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
            8.2,
            2020,
            R.drawable.wolfwalkers
        )
    )

