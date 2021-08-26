package ru.geeekbrains.myfilmapp.model.repository

import ru.geeekbrains.myfilmapp.model.data.Film
import ru.geeekbrains.myfilmapp.model.data.Genre
import ru.geeekbrains.myfilmapp.room.HistoryDao
import ru.geeekbrains.myfilmapp.room.HistoryEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {
    override fun getAllHistory(): List<Film> {
        return convertHistoryEntityToFilm(localDataSource.all())
    }

    private fun convertHistoryEntityToFilm(entityList: List<HistoryEntity>): List<Film> {
        return entityList.map {
            Film("Мстители: Война бесконечности",
                listOf(Genre(12, "приключения"), Genre(28, "боевик")),
                299536,
                "Avengers: Infinity War",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ypX47SBSThTbB40AIJ22eOUCpjU.jpg")
        }
    }

    override fun saveEntity(film: Film) {
        localDataSource.insert(convertWeatherToEntity(film))
    }

    private fun convertWeatherToEntity(film: Film): HistoryEntity {
        return HistoryEntity(299536, "Мстители: Война бесконечности",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ypX47SBSThTbB40AIJ22eOUCpjU.jpg",
        null)
    }


}