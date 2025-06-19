package com.example.justmovie.ui.shows.trending

import com.example.justmovie.model.TrendingShow

data class TrendingShowsState(
    val shows: List<TrendingShow?> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
