package ru.geeekbrains.myfilmapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val poster_path: String?,
    val tagline: String?
)
