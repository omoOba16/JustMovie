package com.example.justmovie.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWithDates(
    @SerialName("dates") val dates: Dates? = null,
    @SerialName("page") val page: Int? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("results") val results: List<Movie?>? = null,
    @SerialName("total_results") val totalResults: Int? = null
)

@Serializable
data class Dates (
    @SerialName("maximum") var maximum : String? = null,
    @SerialName("minimum") var minimum : String? = null
)
