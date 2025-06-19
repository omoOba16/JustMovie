package com.example.justmovie.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justmovie.data.repositories.ShowsRepository
import com.example.justmovie.model.UiState
import com.example.justmovie.ui.shows.ShowState
import com.example.justmovie.ui.shows.trending.TrendingShowsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowViewModel @Inject constructor(
    private val repository: ShowsRepository
) : ViewModel() {

    private var airingTodayPage by mutableIntStateOf(1)
    private var onTheAirPage by mutableIntStateOf(1)
    private var popularPage by mutableIntStateOf(1)
    private var topRatedPage by mutableIntStateOf(1)

    private val _uiStateTrendingShows = MutableStateFlow(TrendingShowsState())
    val uiStateTrendingShows: StateFlow<TrendingShowsState> = _uiStateTrendingShows

    private val _uiStateAiringTodayShows = MutableStateFlow(ShowState())
    val uiStateAiringTodayShows: StateFlow<ShowState> = _uiStateAiringTodayShows

    private val _uiStateOnTheAirShows = MutableStateFlow(ShowState())
    val uiStateOnTheAirShows: StateFlow<ShowState> = _uiStateOnTheAirShows

    private val _uiStatePopularShows = MutableStateFlow(ShowState())
    val uiStatePopularShows: StateFlow<ShowState> = _uiStatePopularShows

    private val _uiStateTopRatedShows = MutableStateFlow(ShowState())
    val uiStateTopRatedShows: StateFlow<ShowState> = _uiStateTopRatedShows

    init {
        getTrendingShows()
        getAiringToday()
        getOnTheAir()
        getPopularShows()
        getTopRatedShows()
    }

    fun getTrendingShows(timeWindow: String = "day") = viewModelScope.launch {
        repository.getTrendingShows(timeWindow).collect { it ->
            when (it) {
                is UiState.Loading -> {
                    Log.d("ShowViewModel", "Loading TrendingShows")
                    _uiStateTrendingShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is UiState.Success -> {
                    val trendingShows = it.data?.results
                    val shows =
                        if (!trendingShows.isNullOrEmpty()) if (trendingShows.size > 4) trendingShows.take(
                            4
                        ) else trendingShows else emptyList()

                    Log.d("ShowViewModel", "Success TrendingShows: ${shows.size}")
                    _uiStateTrendingShows.update {
                        it.copy(
                            shows = shows,
                            isLoading = false
                        )
                    }
                }

                is UiState.Error -> {
                    val errorMessage = it.message
                    Log.d("ShowViewModel", "Error TrendingShows: ${errorMessage}")
                    _uiStateTrendingShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getAiringToday() = viewModelScope.launch {


        repository.getAiringToday(airingTodayPage).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val shows = it.data?.results
                    Log.d("ShowViewModel", "Success getAiringToday: ${shows?.size}")

                    _uiStateAiringTodayShows.update {
                        val existingShows = it.shows
                        val allShows = existingShows + (shows ?: emptyList())
                        val distinctShows = allShows.distinctBy { it?.id }
                        it.copy(
                            shows = distinctShows,
                            hasMoreData = airingTodayPage < totalPages,
                            isLoading = false
                        )
                    }
                    if(airingTodayPage != totalPages){
                        airingTodayPage++
                    }
                }
                is UiState.Loading -> {
                    Log.d("ShowViewModel", "Loading getAiringToday:")
                    _uiStateAiringTodayShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    Log.d("ShowViewModel", "Error getAiringToday: ${errorMessage}")
                    _uiStateAiringTodayShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getOnTheAir() = viewModelScope.launch {

        repository.getOnTheAir(onTheAirPage).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val shows = it.data?.results

                    _uiStateOnTheAirShows.update {
                        val existingShows = it.shows
                        val allShows = existingShows + (shows ?: emptyList())
                        val distinctShows = allShows.distinctBy { it?.id }
                        it.copy(
                            shows = distinctShows,
                            hasMoreData = onTheAirPage < totalPages,
                            isLoading = false
                        )
                    }
                    if(onTheAirPage != totalPages){
                        onTheAirPage++
                    }
                }
                is UiState.Loading -> {
                    _uiStateOnTheAirShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateOnTheAirShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getPopularShows() = viewModelScope.launch {

        repository.getPopularShows(popularPage).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val shows = it.data?.results

                    _uiStatePopularShows.update {
                        val existingShows = it.shows
                        val allShows = existingShows + (shows ?: emptyList())
                        val distinctShows = allShows.distinctBy { it?.id }
                        it.copy(
                            shows = distinctShows,
                            hasMoreData = topRatedPage < totalPages,
                            isLoading = false
                        )
                    }
                    if(popularPage != totalPages){
                        popularPage++
                    }

                }
                is UiState.Loading -> {
                    _uiStatePopularShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStatePopularShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getTopRatedShows() = viewModelScope.launch {

        repository.getTopRatedShows(topRatedPage).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val shows = it.data?.results

                    _uiStateTopRatedShows.update {
                        val existingShows = it.shows
                        val allShows = existingShows + (shows ?: emptyList())
                        val distinctShows = allShows.distinctBy { it?.id }
                        it.copy(
                            shows = distinctShows,
                            hasMoreData = topRatedPage < totalPages,
                            isLoading = false
                        )
                    }
                    if(topRatedPage != totalPages){
                        topRatedPage++
                    }
                }
                is UiState.Loading -> {
                    _uiStateTopRatedShows.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateTopRatedShows.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }
}