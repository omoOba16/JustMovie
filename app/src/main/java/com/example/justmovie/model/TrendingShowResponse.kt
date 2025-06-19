package com.example.justmovie.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingShowResponse(
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val results: List<TrendingShow?>? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("total_results") val totalResults: Int? = null
)
