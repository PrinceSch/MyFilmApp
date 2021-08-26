package ru.geeekbrains.myfilmapp.model.repository

import retrofit2.Call
import retrofit2.http.*
import ru.geeekbrains.myfilmapp.BuildConfig
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO

interface FilmAPI {
    @GET("/3/movie/{movie_id}?")
    fun getFilm(
        @Header("FilmApiKey") token: String,
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = BuildConfig.FILM_API_KEY
    ):Call<MovieResponseDTO>
}