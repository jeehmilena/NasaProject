package br.com.nasaproject.viewmodel.states

import br.com.nasaproject.model.ApodResult

sealed class ApodEvent {
    data class Loading(val visibility: Int): ApodEvent()
    data class Error(val message: String): ApodEvent()
    data class ShowApod(val apod: ApodResult): ApodEvent()
}
