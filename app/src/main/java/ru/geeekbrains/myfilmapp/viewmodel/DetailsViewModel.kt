package ru.geeekbrains.myfilmapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.*
import ru.geeekbrains.myfilmapp.app.App.Companion.getHistoryDao
import ru.geeekbrains.myfilmapp.model.data.Film
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO
import ru.geeekbrains.myfilmapp.model.repository.*
import java.io.IOException

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getFilmFromRemoteSource(id: Int, key: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getFilmDetailsFromServer(id, key, callBack)
    }

    fun saveFilmToDB(film: Film){
        historyRepository.saveEntity(film)
    }

    private val callBack = object : Callback<MovieResponseDTO> {
        @Throws(IOException::class)
        override fun onResponse(
            call: Call<MovieResponseDTO>,
            response: Response<MovieResponseDTO>
        ) {
            val serverResponse: MovieResponseDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: MovieResponseDTO): AppState {
            return if (serverResponse.id == 0) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(serverResponse))
            }
        }

        private fun convertDtoToModel(movieResponseDTO: MovieResponseDTO): List<Film> {
            return listOf(
                Film(
                    movieResponseDTO.title,
                    movieResponseDTO.genreIds,
                    movieResponseDTO.id,
                    movieResponseDTO.original_title,
                    "https://image.tmdb.org/t/p/original" + movieResponseDTO.poster_path,
                    movieResponseDTO.release_date,
                    movieResponseDTO.status,
                    movieResponseDTO.tagline
                )
            )
        }
    }
}