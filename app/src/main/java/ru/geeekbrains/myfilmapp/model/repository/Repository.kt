package ru.geeekbrains.myfilmapp.model.repository

import ru.geeekbrains.myfilmapp.model.data.Film

interface Repository {

    fun getFilmFromServer(): Film
    fun getFilmFromLocalFantasy(): List<Film>
    fun getFilmFromLocalMarvel(): List<Film>
}