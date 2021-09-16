package ru.gb.moviesearcher.ui.main.model

class RepositoryImpl : Repository {

    override fun getNewMoviesFromServer(): List<Movie> = getNewMoviesFromInternet()

    override fun getPopularMoviesFromServer(): List<Movie> = getPopularMoviesFromInternet()


}