package ru.geeekbrains.myfilmapp.model.repository

import ru.geeekbrains.myfilmapp.model.data.Film

class RepositoryImpl : Repository {
    override fun getFilmFromServer(): Film {
        return Film("Harry Potter", 1, null)
    }

    override fun getFilmFromLocal(): Film {
        return Film("The Hitchhiker's Guide to the Galaxy", 42, null)
    }
}