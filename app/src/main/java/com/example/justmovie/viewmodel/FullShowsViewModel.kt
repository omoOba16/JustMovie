package com.example.justmovie.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justmovie.data.repositories.ShowsRepository
import com.example.justmovie.model.ShowCategory
import com.example.justmovie.model.UiState
import com.example.justmovie.ui.shows.ShowState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullShowsViewModel @Inject constructor(
    private val repository: ShowsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var page by mutableIntStateOf(1)
    private val category = savedStateHandle.get<String>("categoryName") ?: ""
    private val _uiStateShows = MutableStateFlow(ShowState())
    val uiStateShows: StateFlow<ShowState> = _uiStateShows

    init {
        getShows()
    }

    fun getShows(){
        when(ShowCategory.fromApiValue(category)){
            ShowCategory.AIRING_TODAY -> getAiringToday()
            ShowCategory.ON_THE_AIR -> getOnTheAir()
            ShowCategory.POPULAR -> getPopularShows()
            ShowCategory.TOP_RATED -> getTopRatedShows()
            null -> {}
        }
    }

    fun getAiringToday() = viewModelScope.launch {

        repository.getAiringToday(page).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val shows = it.data?.results
                    Log.d("ShowViewModel", "Success getAiringToday: ${shows?.size}")

                    _uiStateShows.update {
                        val existingShows = it.shows
                        val allShows = existingShows + (shows ?: emptyList())
                        val distinctShows = allShows.distinctBy { it?.id }
                        it.copy(
                            shows = distinctShows,
                            hasMoreData = page < totalPages,
                            isLoading = false
                        )
                    }
                    if(page != totalPages){
                        page++
                    }
                }
                is UiState.Loading -> {
                    Log.d("ShowViewModel", "Loading getAiringToday:")
                    _uiStateShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    Log.d("ShowViewModel", "Error getAiringToday: ${errorMessage}")
                    _uiStateShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getOnTheAir() = viewModelScope.launch {

        repository.getOnTheAir(page).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val shows = it.data?.results

                    _uiStateShows.update {
                        val existingShows = it.shows
                        val allShows = existingShows + (shows ?: emptyList())
                        val distinctShows = allShows.distinctBy { it?.id }
                        it.copy(
                            shows = distinctShows,
                            hasMoreData = page < totalPages,
                            isLoading = false
                        )
                    }
                    if(page != totalPages){
                        page++
                    }
                }
                is UiState.Loading -> {
                    _uiStateShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getPopularShows() = viewModelScope.launch {

        repository.getPopularShows(page).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val shows = it.data?.results

                    _uiStateShows.update {
                        val existingShows = it.shows
                        val allShows = existingShows + (shows ?: emptyList())
                        val distinctShows = allShows.distinctBy { it?.id }
                        it.copy(
                            shows = distinctShows,
                            hasMoreData = page < totalPages,
                            isLoading = false
                        )
                    }
                    if(page != totalPages){
                        page++
                    }

                }
                is UiState.Loading -> {
                    _uiStateShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getTopRatedShows() = viewModelScope.launch {

        repository.getTopRatedShows(page).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val shows = it.data?.results

                    _uiStateShows.update {
                        val existingShows = it.shows
                        val allShows = existingShows + (shows ?: emptyList())
                        val distinctShows = allShows.distinctBy { it?.id }
                        it.copy(
                            shows = distinctShows,
                            hasMoreData = page < totalPages,
                            isLoading = false
                        )
                    }
                    if(page != totalPages){
                        page++
                    }
                }
                is UiState.Loading -> {
                    _uiStateShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }
}