package com.example.horoscapp.ui.providers

import org.junit.Assert.*
import org.junit.Test

class RandomCardProviderTest{

    @Test
    fun `getRandom should return a random card`(){
        //Given
        val randomCard = RandomCardProvider()

        //When
        val card = randomCard.getLucky()

        //Then
        assertNotNull(card)
    }

}