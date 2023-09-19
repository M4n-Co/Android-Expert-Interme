package com.example.horoscapp.data.network.response

import com.example.horoscapp.motherobject.HoroscopeMotherObject
import io.kotlintest.shouldBe
import org.junit.Test

class PredictionResponseTest{
    @Test
    fun `toDomain should return correct prediction model`(){
        //Given
        val horoscopeResponse = HoroscopeMotherObject.anyResponse

        //When
        val predictionModel = horoscopeResponse.toDomain()

        //Then
        predictionModel.sign shouldBe horoscopeResponse.sign
        predictionModel.horoscope shouldBe horoscopeResponse.horoscope

    }
}