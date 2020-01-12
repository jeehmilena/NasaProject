package br.com.nasaproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.nasaproject.usecase.ApodUseCase
import br.com.nasaproject.viewmodel.states.ApodEvent
import br.com.nasaproject.viewmodel.states.ApodInteractor
import kotlinx.coroutines.launch

class ApodViewModel(val useCase: ApodUseCase) : ViewModel() {

    private val event: MutableLiveData<ApodEvent> = MutableLiveData()
    val viewEvent: LiveData<ApodEvent> = event

    fun interactor(action: ApodInteractor) {
        when (action) {
            is ApodInteractor.ApodDate -> getApodDate(action.date)
        }
    }

    fun getApodDate(date: String) {
        viewModelScope.launch {
            event.value = ApodEvent.Loading()
            try {
                val apod = useCase.apodDate(date)
                event.value = ApodEvent.ShowApod(apod)

            } catch (ex: Exception) {
                 ex.printStackTrace()
                val message = "Try again or use another date from 1995 to 2020"
                event.value = message?.let { ApodEvent.Error(message) }
            }
        }
    }
}