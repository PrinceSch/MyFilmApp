package ru.geeekbrains.myfilmapp.model.dto

data class MovieResponseDTO (
    val title: String,
    val genreIds: List<Int>,
    val id: Int,
    val original_title: String,
    val poster_path: String?,
    val release_date: String,
    val status: String,
    val tagline: String?
        )