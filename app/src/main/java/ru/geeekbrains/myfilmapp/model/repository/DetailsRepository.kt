package ru.geeekbrains.myfilmapp.model.repository

import okhttp3.Callback

interface DetailsRepository {
    fun getFilmDetailsFromServer(requestLink: String, callback: Callback)
}