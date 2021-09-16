package ru.gb.moviesearcher.ui.main.model

class RepositoryImpl : Repository {
    override fun getNewMoviesFromLocalStorage(): List<Movie> = getNewMovies()

    override fun getPopularMoviesFromLocalStorage(): List<Movie> = getPopularMovies()

    override fun getMoviesFromServer(): List<Movie> = getNewMovies()

}