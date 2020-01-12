package br.com.nasaproject.repository

import br.com.nasaproject.model.ApodResult
import br.com.nasaproject.repository.remote.Service
import java.util.*

const val API_KEY = "DEMO_KEY"

class ApodRepository {

    suspend fun getApodDate(date: Date): ApodResult {
        return Service.service.getApodDay(API_KEY, date)
    }
}