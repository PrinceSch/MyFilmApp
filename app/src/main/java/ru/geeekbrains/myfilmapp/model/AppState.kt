package ru.geeekbrains.myfilmapp.model

import ru.geeekbrains.myfilmapp.model.data.Film

sealed class AppState {
    data class Success(val filmData: List<Film>) : AppState()
    class Error (val error : Throwable) : AppState()
    object Loading : AppState()
}