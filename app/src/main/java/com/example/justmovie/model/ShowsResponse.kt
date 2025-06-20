package com.example.justmovie.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowsResponse(
    @SerialName("page") val page: Int? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("results") val results: List<Show?>? = null,
    @SerialName("total_results") val totalResults: Int? = null
)
