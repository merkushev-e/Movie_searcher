package ru.gb.moviesearcher.ui.main.model

class RepositoryImpl : Repository {
    override fun getMoviesFromServer(): Movies {
        return Movies()
    }

    override fun getMoviesFromLocalStorage(): Movies {
        return Movies()
    }

}