package ru.geeekbrains.myfilmapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Film(
    val title: String = "Гарри Поттер и философский камень",
    val genres: @RawValue List<Genre>? = listOf(Genre(12, "приключения"), Genre(14, "фэнтези")),
    val id: Int = 671,
    val original_title: String = "Harry Potter and the Philosopher's Stone",
    val poster_path: String? = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8i7H09Bj25V6ZxaKWybvUYZmyT6.jpg",
    val release_date: String = "2001",
    val status: String = "status",
    val tagline: String? = null,
): Parcelable
