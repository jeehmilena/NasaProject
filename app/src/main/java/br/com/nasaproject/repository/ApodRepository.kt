package br.com.nasaproject.repository

import br.com.nasaproject.repository.remote.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val API_KEY = "DEMO_KEY"

class ApodRepository {

    suspend fun getApodDate(date: String) = withContext(Dispatchers.IO) {
        try {
            return@withContext Service.service.getApodDay(API_KEY, date)

        } catch (exception: Exception) {
            val error = exception.message
            return@withContext error
        }
    }
}