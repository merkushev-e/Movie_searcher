package ru.gb.moviesearcher.ui.main.model

class RepositoryImpl : Repository {
    override fun getMoviesFromServer(): List<Movie> {
        return getWorldCities()
    }

    override fun getMoviesFromLocalStorage(): List<Movie> {
        return getWorldCities()
    }

}