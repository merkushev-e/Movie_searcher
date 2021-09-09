package ru.gb.moviesearcher.ui.main.model

class RepositoryImpl : Repository {
    override fun getNewMoviesFromLocalStorage(): List<Movie> {
        return getNewMovies()
    }

    override fun getPopularMoviesFromLocalStorage(): List<Movie> {
        return getPopularMovies()
    }

    override fun getMoviesFromServer(): List<Movie> {
        return getNewMovies()
    }

}