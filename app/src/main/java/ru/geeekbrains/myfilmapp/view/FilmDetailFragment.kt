package ru.geeekbrains.myfilmapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.squareup.picasso.Picasso
import ru.geeekbrains.myfilmapp.databinding.FragmentFilmDetailBinding
import ru.geeekbrains.myfilmapp.model.DetailsService
import ru.geeekbrains.myfilmapp.model.FILM_ID_EXTRA
import ru.geeekbrains.myfilmapp.model.data.Film
import ru.geeekbrains.myfilmapp.model.dto.MovieResponseDTO

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"

const val DETAILS_TITLE_EXTRA = "TITLE"
const val DETAILS_GENRES_EXTRA = "GENRES IDS"
const val DETAILS_ID_EXTRA = "ID"
const val DETAILS_ORIGTITLE_EXTRA = "ORIGINAL TITLE"
const val DETAILS_POSTER_EXTRA = "POSTER PATH"
const val DETAILS_DATE_EXTRA = "RELEASE DATE"
const val DETAILS_STATUS_EXTRA = "STATUS"
const val DETAILS_TAGLINE_EXTRA = "TAGLINE"

private const val PROCESS_ERROR = "Обработка ошибки"
private const val ID_INVALID = 0


class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> {
                    val title: String = intent.getStringExtra(DETAILS_TITLE_EXTRA)!!
                    val genreIds: List<Int> =
                        intent.getIntegerArrayListExtra(DETAILS_GENRES_EXTRA) as List<Int>
                    val id = intent.getIntExtra(DETAILS_ID_EXTRA, ID_INVALID)
                    val original_title: String = intent.getStringExtra(DETAILS_ORIGTITLE_EXTRA)!!
                    val poster_path: String = intent.getStringExtra(DETAILS_POSTER_EXTRA)!!
                    val release_date: String = intent.getStringExtra(DETAILS_DATE_EXTRA)!!
                    val status: String = intent.getStringExtra(DETAILS_STATUS_EXTRA)!!
                    val tagline: String = intent.getStringExtra(DETAILS_TAGLINE_EXTRA)!!
                    renderData(
                        MovieResponseDTO(title, genreIds, id, original_title, poster_path, release_date, status, tagline)
                    )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Film()
        getFilm()
    }

    private fun getFilm() {
        context?.let {
            it.startService(Intent(it, DetailsService::class.java).apply {
                putExtra(FILM_ID_EXTRA, filmBundle.id)
            })
        }
    }

    private fun renderData(movieResponseDTO: MovieResponseDTO) {
        val id = movieResponseDTO.id
        if (id == ID_INVALID) {
            TODO(PROCESS_ERROR)
        } else {
            with(binding) {
                movieResponseDTO.apply {
                    detailFilmTitle.text = this.title
                    detailFilmOrigtitle.text = this.original_title
                    Picasso.get()
                        .load(this.poster_path)
                        .into(detailFilmPoster)
                }
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

    private fun genresToString(genreList: List<Int>): String {
        var genres = ""
        for (genre in genreList) {
            genres += "${genre}, "
        }
        return genres
    }

    override fun onDestroyView() {
        _binding = null
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroyView()
    }

}