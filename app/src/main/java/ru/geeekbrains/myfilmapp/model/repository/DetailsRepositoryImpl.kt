package ru.geeekbrains.myfilmapp.model.repository

import okhttp3.Callback
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getFilmDetailsFromServer(
        id: Int,
        key: String,
        callback: retrofit2.Callback<MovieResponseDTO>
    ) {
        remoteDataSource.getFilmDetails(id, key, callback)
    }


}