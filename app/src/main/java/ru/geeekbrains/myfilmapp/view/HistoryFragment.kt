package ru.geeekbrains.myfilmapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_history.*
import ru.geeekbrains.myfilmapp.R
import ru.geeekbrains.myfilmapp.databinding.FragmentHistoryBinding
import ru.geeekbrains.myfilmapp.viewmodel.AppState
import ru.geeekbrains.myfilmapp.viewmodel.HistoryViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                with(binding) {
                    historyFragmentRecyclerview.visibility = View.VISIBLE
                    includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
                adapter.setData(appState.filmData)
            }
            is AppState.Loading -> {
                with(binding) {
                    historyFragmentRecyclerview.visibility = View.GONE
                    includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                with(binding) {
                    historyFragmentRecyclerview.visibility = View.VISIBLE
                    includedLoadingLayout.loadingLayout.visibility = View.GONE
                    historyFragmentRecyclerview.showSnakeBar(getString(R.string.error))
                    historyFragmentRecyclerview.showSnakeBar(getString(R.string.reload))
                }
                viewModel.getAllHistory()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}