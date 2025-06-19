package com.example.justmovie.ui.shows

import com.example.justmovie.model.Show

data class ShowState(
    val shows: List<Show?> = emptyList(),
    val isLoading: Boolean = false,
    val hasMoreData: Boolean = true,
    val errorMessage: String? = null
)
