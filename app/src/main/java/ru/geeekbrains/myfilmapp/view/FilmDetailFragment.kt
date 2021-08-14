package ru.geeekbrains.myfilmapp.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import ru.geeekbrains.myfilmapp.BuildConfig
import ru.geeekbrains.myfilmapp.databinding.FragmentFilmDetailBinding
import ru.geeekbrains.myfilmapp.model.data.Film
import ru.geeekbrains.myfilmapp.model.data.Genre
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable<Film>(BUNDLE_EXTRA) ?: Film()
//        binding.main.hide()
//        binding.loadingLayout.show()
        loadFilms()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadFilms() {
        try{
            val uri = URL("https://api.themoviedb.org/3/movie/672?api_key=8f8dfd9a82fff31a2d5d507cd548c1c1&language=en-US")
            val handler = Handler(Looper.myLooper()!!)
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try{
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "api_key", BuildConfig.FILM_API_KEY
                    )
                    urlConnection.readTimeout = 100000
                    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

                    val movieResponseDTO: MovieResponseDTO = Gson().fromJson(getLines(bufferedReader), MovieResponseDTO::class.java)
                    handler.post { displayFilm(movieResponseDTO) }
                } catch (e: Exception) {
                    Log.e("FILM", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("FILM", "Bad URL", e)
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayFilm(movieResponseDTO: MovieResponseDTO){
        with(binding) {
            movieResponseDTO?.let { detailFilmTitle.text = it.title
                detailFilmOrigtitle.text = it.original_title
                Picasso.get()
                    .load(it.poster_path)
                    .into(detailFilmPoster) }
            }
    }

    private fun populateData(filmData: Film) {
        with(binding) {
            filmData.also {
                detailFilmTitle.text = it.title
                detailFilmGenres.text = it.genres.toString()
                detailFilmOrigtitle.text = it.original_title
                Picasso.get()
                    .load(it.poster_path)
                    .into(detailFilmPoster)
            }
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "film"

        fun newInstance(bundle: Bundle): FilmDetailFragment {
            val fragment = FilmDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun genresToString(genreList: List<Int>) : String{
        var genres = ""
        for (genre in genreList){
            genres += "${genre}, "
        }
        return genres
    }

}