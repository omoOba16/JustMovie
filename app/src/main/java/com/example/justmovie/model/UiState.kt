package com.example.justmovie.model

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val message: String) : UiState<T>()
}