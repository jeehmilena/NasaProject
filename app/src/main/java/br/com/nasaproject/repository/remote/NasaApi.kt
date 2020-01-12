package br.com.nasaproject.repository.remote

import br.com.nasaproject.model.ApodResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    suspend fun getApodDay(
        @Query("api_key") api: String,
        @Query("date") date: String
    ): ApodResult
}