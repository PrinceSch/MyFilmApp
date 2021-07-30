package ru.geeekbrains.myfilmapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import ru.geeekbrains.myfilmapp.R
import ru.geeekbrains.myfilmapp.databinding.MainFragmentBinding
import ru.geeekbrains.myfilmapp.model.AppState
import ru.geeekbrains.myfilmapp.viewmodel.MainViewModel

class MainFragment : Fragment() {



    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<AppState> { a -> renderData(a)}

        viewModel.getData().observe(viewLifecycleOwner, observer)
        binding.button.setOnClickListener {
            viewModel.requestData(binding.message.text as String)
        }
    }

    private fun renderData(data: AppState){
        when(data){
            is AppState.Success -> {
                val weatherData = data.weatherData
                binding.loadingLayout.visibility = View.GONE
                binding.message.text = weatherData
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.main, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.requestData(binding.message.text as String) }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}