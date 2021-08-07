package ru.geeekbrains.myfilmapp.view

import android.content.Context
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
import ru.geeekbrains.myfilmapp.model.data.Film
import ru.geeekbrains.myfilmapp.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
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
        adapter.setOnItemViewClickListener(object: OnItemViewClickListener{
            override fun onItemViewClick(film: Film) {
                val manager = activity?.supportFragmentManager
                if (manager != null){
                    val bundle = Bundle()
                    bundle.putParcelable(FilmDetailFragment.BUNDLE_EXTRA, film)
                    manager.beginTransaction()
                        .replace(R.id.container, FilmDetailFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentRecyclerView.layoutManager = GridLayoutManager(context,3)
        binding.switchFilmData.setOnClickListener{
            changeFilmDataSet()
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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
                binding.loadingLayout.visibility = View.GONE
                adapter.setFilm(filmData)
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.main, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") {
                        if (isDataSetFantasy) {
                            viewModel.getFilmFromLocalFantasy()
                        } else {
                            viewModel.getFilmFromLocalMarvel()
                        }
                    }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeOnItemViewClickListener()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }

    fun loadDataSet(){
        if (isDataSetFantasy) {
            viewModel.getFilmFromLocalFantasy()
        } else {
            viewModel.getFilmFromLocalMarvel()
        }
    }
}