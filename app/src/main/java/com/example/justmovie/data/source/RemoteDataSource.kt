package com.example.justmovie.data.source

import com.example.justmovie.network.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    // Movies
    suspend fun getTrendingMovies(timeWindow: String) = apiService.getTrendingMovies(timeWindow = timeWindow)
    suspend fun getNowPlayingMovies(page:Int?) = apiService.getNowPlayingMovies(page = page)
    suspend fun getPopularMovies(page:Int?) = apiService.getPopularMovies(page = page)
    suspend fun getTopRatedMovies(page:Int?) = apiService.getTopRatedMovies(page = page)
    suspend fun getUpcomingMovies(page:Int?) = apiService.getUpcomingMovies(page = page)

    // Tv-shows
    suspend fun getTrendingShows(timeWindow: String) = apiService.getTrendingShows(timeWindow = timeWindow)
    suspend fun getAiringToday(page:Int?) = apiService.getAiringToday(page = page)
    suspend fun getOnTheAir(page:Int?) = apiService.getOnTheAir(page = page)
    suspend fun getPopularShows(page:Int?) = apiService.getPopularShows(page = page)
    suspend fun getTopRatedShows(page:Int?) = apiService.getTopRatedShows(page = page)
}