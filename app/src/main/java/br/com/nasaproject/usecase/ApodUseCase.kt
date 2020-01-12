package br.com.nasaproject.usecase

import br.com.nasaproject.model.ApodResult
import br.com.nasaproject.repository.ApodRepository
import java.util.*

class ApodUseCase(val repository: ApodRepository) {

    suspend fun apodDate(date: Date): ApodResult = repository.getApodDate(date)
}