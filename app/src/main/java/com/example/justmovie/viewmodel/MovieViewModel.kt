package com.example.justmovie.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justmovie.data.repositories.MoviesRepository
import com.example.justmovie.model.UiState
import com.example.justmovie.ui.movies.MovieState
import com.example.justmovie.ui.movies.trending.TrendingMoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    private var nowPlayingPage by mutableIntStateOf(1)
    private var popularPage by mutableIntStateOf(1)
    private var topRatedPage by mutableIntStateOf(1)
    private var upcomingPage by mutableIntStateOf(1)

    private val _uiStateTrendingMovies = MutableStateFlow(TrendingMoviesState())
    val uiStateTrendingMovies: StateFlow<TrendingMoviesState> = _uiStateTrendingMovies

    private val _uiStateNowPlayingMovies = MutableStateFlow(MovieState())
    val uiStateNowPlayingMovies: StateFlow<MovieState> = _uiStateNowPlayingMovies

    private val _uiStatePopularMovies = MutableStateFlow(MovieState())
    val uiStatePopularMovies: StateFlow<MovieState> = _uiStatePopularMovies

    private val _uiStateTopRatedMovies = MutableStateFlow(MovieState())
    val uiStateTopRatedMovies: StateFlow<MovieState> = _uiStateTopRatedMovies

    private val _uiStateUpcomingMovies = MutableStateFlow(MovieState())
    val uiStateUpcomingMovies: StateFlow<MovieState> = _uiStateUpcomingMovies

    init {
        getTrendingMovies()
        getNowPlayingMovies()
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    fun getTrendingMovies(timeWindow: String = "day") = viewModelScope.launch {
        repository.getTrendingMovies(timeWindow).collect { it ->
            when (it) {
                is UiState.Loading -> {
                    _uiStateTrendingMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is UiState.Success -> {
                    val trendingMovies = it.data?.results
                    val movies =
                        if (!trendingMovies.isNullOrEmpty()) if (trendingMovies.size > 4) trendingMovies.take(
                            4
                        ) else trendingMovies else emptyList()
                    _uiStateTrendingMovies.update {
                        it.copy(
                            trendingMovies = movies,
                            isLoading = false
                        )
                    }
                }

                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateTrendingMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getNowPlayingMovies() = viewModelScope.launch {

        repository.getNowPlayingMovies(nowPlayingPage).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val movies = it.data?.results

                    _uiStateNowPlayingMovies.update {
                        val existingMovies = it.movies
                        val allMovies = existingMovies + (movies ?: emptyList())
                        val distinctMovies = allMovies.distinctBy { it?.id }
                        it.copy(
                            movies = distinctMovies,
                            hasMoreData = nowPlayingPage < totalPages,
                            isLoading = false
                        )
                    }
                    if(nowPlayingPage != totalPages){
                        nowPlayingPage++
                    }
                }
                is UiState.Loading -> {
                    _uiStateNowPlayingMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateNowPlayingMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getPopularMovies() = viewModelScope.launch {

        repository.getPopularMovies(popularPage).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val movies = it.data?.results

                    _uiStatePopularMovies.update {
                        val existingMovies = it.movies
                        val allMovies = existingMovies + (movies ?: emptyList())
                        val distinctMovies = allMovies.distinctBy { it?.id }
                        it.copy(
                            movies = distinctMovies,
                            hasMoreData = popularPage < totalPages,
                            isLoading = false
                        )
                    }
                    if(popularPage != totalPages){
                        popularPage++
                    }
                }
                is UiState.Loading -> {
                    _uiStatePopularMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStatePopularMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getTopRatedMovies() = viewModelScope.launch {

        repository.getTopRatedMovies(topRatedPage).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val movies = it.data?.results

                    _uiStateTopRatedMovies.update {
                        val existingMovies = it.movies
                        val allMovies = existingMovies + (movies ?: emptyList())
                        val distinctMovies = allMovies.distinctBy { it?.id }
                        it.copy(
                            movies = distinctMovies,
                            hasMoreData = topRatedPage < totalPages,
                            isLoading = false
                        )
                    }
                    if(topRatedPage != totalPages){
                        topRatedPage++
                    }

                }
                is UiState.Loading -> {
                    _uiStateTopRatedMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateTopRatedMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getUpcomingMovies() = viewModelScope.launch {

        repository.getUpcomingMovies(upcomingPage).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val movies = it.data?.results

                    _uiStateUpcomingMovies.update {
                        val existingMovies = it.movies
                        val allMovies = existingMovies + (movies ?: emptyList())
                        val distinctMovies = allMovies.distinctBy { it?.id }
                        it.copy(
                            movies = distinctMovies,
                            hasMoreData = upcomingPage < totalPages,
                            isLoading = false
                        )
                    }
                    if(upcomingPage != totalPages){
                        upcomingPage++
                    }
                }
                is UiState.Loading -> {
                    _uiStateUpcomingMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateUpcomingMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }
}