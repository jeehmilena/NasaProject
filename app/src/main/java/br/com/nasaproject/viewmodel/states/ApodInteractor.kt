package br.com.nasaproject.viewmodel.states

import java.util.*

sealed class ApodInteractor {
    data class ApodDate(val date: Date): ApodInteractor()
}