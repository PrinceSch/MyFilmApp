package ru.geeekbrains.myfilmapp.model

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.geeekbrains.myfilmapp.BuildConfig
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO
import ru.geeekbrains.myfilmapp.view.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val FILM_ID_EXTRA = "Film ID"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000
private const val REQUEST_API_KEY = "FILM_API_KEY"

class DetailsService(name: String = "DetailService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(FILM_ID_EXTRA, 0)
            if (id == 0) {
                onEmptyData()
            } else {
                loadFilm(id.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadFilm(id: String) {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${id}?api_key=${BuildConfig.FILM_API_KEY}1&language=ru-RU")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                    addRequestProperty(REQUEST_API_KEY, BuildConfig.FILM_API_KEY)
                }
                val movieResponseDTO: MovieResponseDTO =
                    Gson().fromJson(
                        getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))),
                        MovieResponseDTO::class.java
                    )
                onResponse(movieResponseDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(movieResponseDTO: MovieResponseDTO) {
        if (movieResponseDTO.id == 0) {
            onEmptyResponse()
        } else {
            onSuccessResponse(movieResponseDTO)
        }
    }

    private fun onSuccessResponse(movieResponseDTO: MovieResponseDTO) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        with(movieResponseDTO) {
            broadcastIntent.putExtra(DETAILS_TITLE_EXTRA, title)
            broadcastIntent.putExtra(DETAILS_GENRES_EXTRA, genreIds as IntArray)
            broadcastIntent.putExtra(DETAILS_ID_EXTRA, id)
            broadcastIntent.putExtra(DETAILS_ORIGTITLE_EXTRA, original_title)
            broadcastIntent.putExtra(DETAILS_POSTER_EXTRA, poster_path)
            broadcastIntent.putExtra(DETAILS_DATE_EXTRA, release_date)
            broadcastIntent.putExtra(DETAILS_STATUS_EXTRA, status)
            broadcastIntent.putExtra(DETAILS_TAGLINE_EXTRA, tagline)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

}