package ru.geeekbrains.myfilmapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geeekbrains.myfilmapp.model.repository.Repository
import ru.geeekbrains.myfilmapp.model.repository.RepositoryImpl
import java.lang.Thread.*

class MainViewModel(private val repository: Repository = RepositoryImpl()) : ViewModel() {
    private val myLoadingTime: Long = 1000

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getData(): LiveData<AppState> = liveDataToObserve

    fun getFilmFromLocalFantasy() = getDataFromLocalSource(isFantasy = true)

    fun getFilmFromLocalMarvel() = getDataFromLocalSource(isFantasy = false)

    private fun getDataFromLocalSource(isFantasy: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(myLoadingTime)
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isFantasy) {
                        repository.getFilmFromLocalFantasy()
                    } else {
                        repository.getFilmFromLocalMarvel()
                    }
                )
            )
        }.start()
    }


}