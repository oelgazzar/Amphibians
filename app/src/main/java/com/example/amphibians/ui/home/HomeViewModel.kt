package com.example.amphibians.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.MyApplication
import com.example.amphibians.data.AmphibianRepository
import com.example.amphibians.models.Amphibian
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface UiState {
    object Loading: UiState
    data class Success(val amphibians: List<Amphibian>): UiState
    data class Error(val message: String): UiState
}



class MainViewModel(
    private val repository: AmphibianRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        println("init called")
        getAmphibians()
    }

    fun getAmphibians() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val amphibians = repository.getAmphibians()
                _uiState.value = UiState.Success(amphibians)
            } catch (_: IOException) {
                _uiState.value = UiState.Error("Error loading amphibians")
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