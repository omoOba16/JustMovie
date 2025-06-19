package com.example.justmovie.ui.movies

import com.example.justmovie.model.Movie

data class MovieState(
    val movies: List<Movie?> = emptyList(),
    val isLoading: Boolean = false,
    val hasMoreData: Boolean = true,
    val errorMessage: String? = null
)