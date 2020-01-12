package br.com.nasaproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.nasaproject.usecase.ApodUseCase

class ApodViewModelFactory(val useCase: ApodUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ApodUseCase::class.java)
            .newInstance(useCase)
    }
}