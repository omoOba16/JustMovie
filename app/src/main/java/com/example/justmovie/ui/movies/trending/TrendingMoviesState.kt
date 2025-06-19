package com.example.justmovie.ui.movies.trending

import com.example.justmovie.model.Movie

data class TrendingMoviesState(
    val trendingMovies: List<Movie?> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
