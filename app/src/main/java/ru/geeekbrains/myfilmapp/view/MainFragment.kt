package ru.geeekbrains.myfilmapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ru.geeekbrains.myfilmapp.R
import ru.geeekbrains.myfilmapp.databinding.MainFragmentBinding
import ru.geeekbrains.myfilmapp.viewmodel.AppState
import ru.geeekbrains.myfilmapp.viewmodel.MainViewModel

//2. Добавьте возможность оставлять заметку о просмотренном фильме, чтобы сохранять её в базе данных.
//3. Создайте БД для вашего приложения и храните там историю запросов: сведения о фильмах, время просмотра, заметку о фильме.
//4. Выполняйте запросы в БД асинхронно (в рабочем потоке).


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val SPAN_LAYOUT = 3
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
            mainFragmentRecyclerView.layoutManager = GridLayoutManager(context, SPAN_LAYOUT)
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
                binding.includedLoadingLayout.loadingLayout.hide()
                adapter.setFilm(filmData)
            }

            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.show()
            }

            is AppState.Error -> {
                with(binding) {
                    includedLoadingLayout.loadingLayout.hide()
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