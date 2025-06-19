package com.example.justmovie.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justmovie.data.repositories.MoviesRepository
import com.example.justmovie.model.MovieCategory
import com.example.justmovie.model.UiState
import com.example.justmovie.ui.movies.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.distinctBy
import kotlin.collections.plus


@HiltViewModel
class FullMoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var page by mutableIntStateOf(1)
    private val category = savedStateHandle.get<String>("categoryName") ?: ""
    private val _uiStateMovies = MutableStateFlow(MovieState())
    val uiStateMovies: StateFlow<MovieState> = _uiStateMovies

    init {
        getMovies()
    }

    fun getMovies(){
        when(MovieCategory.fromApiValue(category)){
            MovieCategory.NOW_PLAYING -> getNowPlayingMovies()
            MovieCategory.POPULAR -> getPopularMovies()
            MovieCategory.TOP_RATED -> getTopRatedMovies()
            MovieCategory.UPCOMING -> getUpcomingMovies()
            null -> {}
        }
    }

    fun getNowPlayingMovies() = viewModelScope.launch {

        repository.getNowPlayingMovies(page).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val movies = it.data?.results

                    _uiStateMovies.update {
                        val existingMovies = it.movies
                        val allMovies = existingMovies + (movies ?: emptyList())
                        val distinctMovies = allMovies.distinctBy { it?.id }
                        it.copy(
                            movies = distinctMovies,
                            hasMoreData = page < totalPages,
                            isLoading = false
                        )
                    }
                    if(page != totalPages){
                        page++
                    }
                }
                is UiState.Loading -> {
                    _uiStateMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getPopularMovies() = viewModelScope.launch {

        repository.getPopularMovies(page).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val movies = it.data?.results

                    _uiStateMovies.update {
                        val existingMovies = it.movies
                        val allMovies = existingMovies + (movies ?: emptyList())
                        val distinctMovies = allMovies.distinctBy { it?.id }
                        it.copy(
                            movies = distinctMovies,
                            hasMoreData = page < totalPages,
                            isLoading = false
                        )
                    }
                    if(page != totalPages){
                        page++
                    }
                }
                is UiState.Loading -> {
                    _uiStateMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getTopRatedMovies() = viewModelScope.launch {

        repository.getTopRatedMovies(page).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val movies = it.data?.results

                    _uiStateMovies.update {
                        val existingMovies = it.movies
                        val allMovies = existingMovies + (movies ?: emptyList())
                        val distinctMovies = allMovies.distinctBy { it?.id }
                        it.copy(
                            movies = distinctMovies,
                            hasMoreData = page < totalPages,
                            isLoading = false
                        )
                    }
                    if(page != totalPages){
                        page++
                    }

                }
                is UiState.Loading -> {
                    _uiStateMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getUpcomingMovies() = viewModelScope.launch {

        repository.getUpcomingMovies(page).collect { it ->
            when (it) {
                is UiState.Success -> {
                    val totalPages = it.data?.totalPages ?: 1
                    val movies = it.data?.results

                    _uiStateMovies.update {
                        val existingMovies = it.movies
                        val allMovies = existingMovies + (movies ?: emptyList())
                        val distinctMovies = allMovies.distinctBy { it?.id }
                        it.copy(
                            movies = distinctMovies,
                            hasMoreData = page < totalPages,
                            isLoading = false
                        )
                    }
                    if(page != totalPages){
                        page++
                    }
                }
                is UiState.Loading -> {
                    _uiStateMovies.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is UiState.Error -> {
                    val errorMessage = it.message
                    _uiStateMovies.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }
}