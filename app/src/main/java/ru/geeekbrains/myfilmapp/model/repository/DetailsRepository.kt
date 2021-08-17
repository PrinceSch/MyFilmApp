package ru.geeekbrains.myfilmapp.model.repository

import retrofit2.Callback
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO

interface DetailsRepository {
    fun getFilmDetailsFromServer(id: Int, key: String, callback: Callback<MovieResponseDTO>)
}