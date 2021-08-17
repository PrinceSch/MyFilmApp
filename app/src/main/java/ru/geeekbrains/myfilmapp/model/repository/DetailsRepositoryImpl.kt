package ru.geeekbrains.myfilmapp.model.repository

import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getFilmDetailsFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getFilmDetails(requestLink, callback)
    }

}