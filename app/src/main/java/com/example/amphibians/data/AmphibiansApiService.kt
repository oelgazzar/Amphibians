package com.example.amphibians.data

import com.example.amphibians.models.Amphibian
import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}