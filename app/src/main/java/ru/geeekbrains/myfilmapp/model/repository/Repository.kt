package ru.geeekbrains.myfilmapp.model.repository

import ru.geeekbrains.myfilmapp.model.data.Film

interface Repository {

    fun getFilmFromServer(): Film
    fun getFilmFromLocal(): Film
}