package ru.geeekbrains.myfilmapp.model.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geeekbrains.myfilmapp.BuildConfig
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO

class RemoteDataSource {

    private val filmAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .build().create(FilmAPI::class.java)

    fun getFilmDetails(id: Int, key: String, callback: Callback<MovieResponseDTO>){
        filmAPI.getFilm(BuildConfig.FILM_API_KEY, id, key).enqueue(callback)
    }
}