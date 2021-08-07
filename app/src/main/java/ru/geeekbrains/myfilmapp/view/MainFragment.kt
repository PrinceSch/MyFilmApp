package ru.geeekbrains.myfilmapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.geeekbrains.myfilmapp.R
import ru.geeekbrains.myfilmapp.databinding.MainFragmentBinding
import ru.geeekbrains.myfilmapp.model.AppState
import ru.geeekbrains.myfilmapp.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter()
    private var isDataSetFantasy: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener { film ->
                activity?.supportFragmentManager?.apply {
                    this.beginTransaction()
                        .replace(R.id.container, FilmDetailFragment.newInstance(Bundle().apply {
                            putParcelable(FilmDetailFragment.BUNDLE_EXTRA, film)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }

        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentRecyclerView.layoutManager = GridLayoutManager(context, 3)
            switchFilmData.setOnClickListener {
                changeFilmDataSet()
            }
        }

        val observer = Observer<AppState> { appStateData -> renderData(appStateData) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        loadDataSet()
    }

    private fun changeFilmDataSet() {
        isDataSetFantasy = !isDataSetFantasy
        loadDataSet()
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                val filmData = data.filmData
                binding.loadingLayout.hide()
                adapter.setFilm(filmData)
            }

            is AppState.Loading -> {
                binding.loadingLayout.show()
            }

            is AppState.Error -> {
                with(binding) {
                    loadingLayout.hide()
                    switchFilmData.showSnakeBar("Reload")

                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadDataSet() {
        if (isDataSetFantasy) {
            viewModel.getFilmFromLocalFantasy()
        } else {
            viewModel.getFilmFromLocalMarvel()
        }
    }
}