package ru.geeekbrains.myfilmapp.model.data

data class Film(
    val title: String,
//    val genres: HashMap<Int, String>,
    //здесь вопрос - правильно ли объявила? в api синтаксис такой: genres: array[object], id: integer, name: string
    val id: Int,
//    val original_title: String,
    val poster_path: String?,
//    val release_date: String,
//    val status: String,
//    val tagline: String?
)
