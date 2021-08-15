package ru.geeekbrains.myfilmapp.model.dto

data class ResponseDTO (
    val page: Int,
    val result: List<MovieResponseDTO>,
    val totalPages: Int,
    val totalResults: Int
)