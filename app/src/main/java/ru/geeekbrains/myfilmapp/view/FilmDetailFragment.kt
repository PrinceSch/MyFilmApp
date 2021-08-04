package ru.geeekbrains.myfilmapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import ru.geeekbrains.myfilmapp.databinding.FragmentFilmDetailBinding
import ru.geeekbrains.myfilmapp.model.data.Film


class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
        if (film != null){
            populateData(film)
        }
    }

    fun populateData(filmData: Film){
        with(binding){
            detailFilmTitle.text = filmData.title
            detailFilmGenres.text = filmData.genres[0].name + ", " + filmData.genres[1].name
            detailFilmOrigtitle.text = filmData.original_title
            Picasso.with(context)
                .load(filmData.poster_path)
                .into(detailFilmPoster)
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "film"

        fun newInstance(bundle: Bundle): FilmDetailFragment{
            val fragment = FilmDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}