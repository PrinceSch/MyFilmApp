package ru.geeekbrains.myfilmapp.model.repository

import ru.geeekbrains.myfilmapp.model.data.Film
import ru.geeekbrains.myfilmapp.model.data.Genre
import ru.geeekbrains.myfilmapp.model.data.getFantasyFilms
import ru.geeekbrains.myfilmapp.model.data.getMarvelFilms

class RepositoryImpl : Repository {
    override fun getFilmFromServer() = Film(
        "Гарри Поттер и философский камень",
        listOf(Genre(12, "приключения"), Genre(14, "фэнтези")),
        671,
        "Harry Potter and the Philosopher's Stone",
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8i7H09Bj25V6ZxaKWybvUYZmyT6.jpg",
        "2001",
        "status",
        null
    )

override fun getFilmFromLocalFantasy() = getFantasyFilms()

override fun getFilmFromLocalMarvel() = getMarvelFilms()

}