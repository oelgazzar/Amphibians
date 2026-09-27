package com.example.amphibians.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.MyApplication
import com.example.amphibians.data.AmphibianRepository
import com.example.amphibians.models.Amphibian
import kotlinx.coroutines.launch
import okio.IOException

sealed interface UiState {
    object Loading: UiState
    data class Success(val amphibians: List<Amphibian>): UiState
    data class Error(val message: String): UiState
}



class MainViewModel(
    private val repository: AmphibianRepository
): ViewModel() {
    var uiState: UiState by mutableStateOf(UiState.Loading)
    private set

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        uiState = UiState.Loading
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val amphibians = repository.getAmphibians()
                uiState = UiState.Success(amphibians)
            } catch (e: IOException) {
                uiState = UiState.Error("Error loading amphibians: ${e.message}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MyApplication)
                val amphibiansRepository = application.appContainer.amphibiansRepository
                MainViewModel(amphibiansRepository)
            }
        }
    }
}