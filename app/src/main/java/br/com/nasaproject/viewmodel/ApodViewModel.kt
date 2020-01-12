package br.com.nasaproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.nasaproject.usecase.ApodUseCase
import br.com.nasaproject.viewmodel.states.ApodEvent
import br.com.nasaproject.viewmodel.states.ApodInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class ApodViewModel(val useCase: ApodUseCase) : ViewModel() {

    private val event: MutableLiveData<ApodEvent> = MutableLiveData()
    val viewEvent: LiveData<ApodEvent> = event

    suspend fun interactor(action: ApodInteractor) {
        when (action) {
            is ApodInteractor.ApodDate -> getApodDate(action.date)
        }
    }

    suspend fun getApodDate(date: Date) = withContext(Dispatchers.IO) {
        try {
            val apod = useCase.apodDate(date)
            event.value = ApodEvent.ShowApod(apod)

        } catch (ex: Exception) {
            val message = ex.message
            event.value = message?.let { ApodEvent.Error(it) }
        }
    }
}