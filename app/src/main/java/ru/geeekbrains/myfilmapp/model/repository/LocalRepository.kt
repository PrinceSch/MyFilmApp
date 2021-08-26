package ru.geeekbrains.myfilmapp.model.repository

import ru.geeekbrains.myfilmapp.model.data.Film

interface LocalRepository {
    fun getAllHistory(): List<Film>
    fun saveEntity(film: Film)
}