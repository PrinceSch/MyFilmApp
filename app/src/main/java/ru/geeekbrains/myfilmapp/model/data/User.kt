package ru.geeekbrains.myfilmapp.model.data

data class User(
    val id: Int,
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val username: String
)
