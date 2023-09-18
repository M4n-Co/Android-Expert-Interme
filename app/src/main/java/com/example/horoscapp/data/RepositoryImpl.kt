package com.example.horoscapp.data

import android.util.Log
import com.example.horoscapp.data.network.HoroscopeApiService
import com.example.horoscapp.data.network.response.PredictionResponse
import com.example.horoscapp.domain.Repository
import com.example.horoscapp.domain.model.PredictionModel
import javax.inject.Inject
import kotlin.math.sign

class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService) : Repository {
    override suspend fun getPrediction(sign: String) : PredictionModel? {
        //Peticion
        val response = runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("Error", "Ha ocurrido un error${it.message}") }

        return null
    }
}