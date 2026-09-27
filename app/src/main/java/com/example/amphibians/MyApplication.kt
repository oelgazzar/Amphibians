package com.example.amphibians

import android.app.Application
import com.example.amphibians.data.AmphibianRepository
import com.example.amphibians.data.AmphibiansApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://android-kotlin-fun-mars-server.appspot.com/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    private  val apiService = retrofit.create(AmphibiansApiService::class.java)
    val amphibiansRepository = AmphibianRepository(apiService)
}

class MyApplication: Application() {
    val appContainer = AppContainer()
}