package ru.geeekbrains.myfilmapp.model.dto

import ru.geeekbrains.myfilmapp.model.data.Genre

data class MovieResponseDTO (
    val title: String,
    val genreIds: List<Genre>?,
    val id: Int,
    val original_title: String,
    val poster_path: String?,
    val release_date: String,
    val status: String,
    val tagline: String?
        )