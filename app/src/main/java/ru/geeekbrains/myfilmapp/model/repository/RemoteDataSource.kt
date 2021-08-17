package ru.geeekbrains.myfilmapp.model.repository

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.geeekbrains.myfilmapp.BuildConfig

private const val REQUEST_API_KEY = "FILM_API_KEY"

class RemoteDataSource {
    fun getFilmDetails(requestLink: String, callback: Callback) {
        val builder: Request.Builder = Request.Builder().apply {
            header(REQUEST_API_KEY, BuildConfig.FILM_API_KEY)
            url(requestLink)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}