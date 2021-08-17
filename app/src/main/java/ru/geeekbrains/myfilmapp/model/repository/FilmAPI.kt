package ru.geeekbrains.myfilmapp.model.repository

import retrofit2.Call
import retrofit2.http.*
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO

interface FilmAPI {
    @GET("3/movie/")
    fun getFilm(
        @Header("FilmApiKey") token: String,
        @Query("id") id: Int,
        @Query("api_key") key: String
    ):Call<MovieResponseDTO>
}