package ru.geeekbrains.myfilmapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import ru.geeekbrains.myfilmapp.databinding.FragmentFilmDetailBinding
import ru.geeekbrains.myfilmapp.model.data.Film
import ru.geeekbrains.myfilmapp.model.data.Genre


class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Film>(BUNDLE_EXTRA)?.let { populateData(it) }
    }

    private fun populateData(filmData: Film) {
        with(binding) {
            filmData.also {
                detailFilmTitle.text = it.title
                detailFilmGenres.text = genresToString(it.genres)
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

    private fun genresToString(genreList: List<Genre>) : String{
        var genres = ""
        for (genre in genreList){
            genres += "${genre.name}, "
        }
        return genres
    }

}