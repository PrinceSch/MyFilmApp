package ru.geeekbrains.myfilmapp.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.geeekbrains.myfilmapp.BuildConfig
import ru.geeekbrains.myfilmapp.R
import ru.geeekbrains.myfilmapp.databinding.FragmentFilmDetailBinding
import ru.geeekbrains.myfilmapp.viewmodel.AppState
import ru.geeekbrains.myfilmapp.model.data.Film
import ru.geeekbrains.myfilmapp.model.data.Genre
import ru.geeekbrains.myfilmapp.viewmodel.DetailsViewModel



class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

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
        filmBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Film()
        viewModel.detailsLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getFilmFromRemoteSource(filmBundle.id, BuildConfig.FILM_API_KEY)
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
                with(binding) {
                    mainDetailFragmentView.visibility = View.VISIBLE
                    includedLoadingLayout.loadingLayout.visibility = View.GONE
                    setFilm(appState.filmData)
                }
            }
            is AppState.Loading -> {
                with(binding) {
                    mainDetailFragmentView.visibility = View.GONE
                    includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                with(binding) {
                    mainDetailFragmentView.visibility = View.VISIBLE
                    includedLoadingLayout.loadingLayout.visibility = View.GONE
                    mainDetailFragmentView.showSnakeBar(getString(R.string.error))
                    mainDetailFragmentView.showSnakeBar(getString(R.string.reload))
                }
                viewModel.getFilmFromRemoteSource(filmBundle.id, BuildConfig.FILM_API_KEY)
            }
        }
    }

    private fun setFilm(filmData: List<Film>) {
        filmData[0].apply {
            saveFilm(this)
            with(binding){
                detailFilmTitle.text = title
                genres?.let {detailFilmGenres.text = genresToString(genres)}
                detailFilmOrigtitle.text = original_title
                Picasso
                    .get()
                    .load(poster_path)
                    .into(detailFilmPoster)
            }
        }
    }

    private fun saveFilm (film: Film){
        viewModel.saveFilmToDB(film)
    }

    companion object {
        const val BUNDLE_EXTRA = "film"

        fun newInstance(bundle: Bundle): FilmDetailFragment {
            val fragment = FilmDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun genresToString(genreList: List<Genre>): String {
        var genres = ""
        for (genre in genreList) {
            genres += "${genre.name}, "
        }
        return genres
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}