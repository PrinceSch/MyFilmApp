package ru.geeekbrains.myfilmapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Film(
    val title: String,
    val genres: @RawValue List<Genre>,
    val id: Int,
    val original_title: String,
    val poster_path: String?,
//    val release_date: String,
//    val status: String,
//    val tagline: String?
): Parcelable
