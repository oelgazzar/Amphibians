package com.example.amphibians.data

import com.example.amphibians.models.Amphibian
import kotlinx.coroutines.delay
import java.io.IOException
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class AmphibianRepository(
    private val amphibiansApiService: AmphibiansApiService
) {
    suspend fun getAmphibians(): List<Amphibian> {
        delay(2000.milliseconds)
        if (Random.nextDouble() < .0) {
            throw IOException("Network Error")
        }
        return amphibiansApiService.getAmphibians()
    }
}