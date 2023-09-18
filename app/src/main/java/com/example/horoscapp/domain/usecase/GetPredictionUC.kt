package com.example.horoscapp.domain.usecase

import com.example.horoscapp.domain.Repository
import javax.inject.Inject

class GetPredictionUC @Inject constructor(private val repository: Repository){
    suspend operator fun invoke(sign : String) = repository.getPrediction(sign)

}