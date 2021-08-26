package ru.geeekbrains.myfilmapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geeekbrains.myfilmapp.app.App.Companion.getHistoryDao
import ru.geeekbrains.myfilmapp.model.repository.LocalRepository
import ru.geeekbrains.myfilmapp.model.repository.LocalRepositoryImpl

class HistoryViewModel(val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
                       private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }

}