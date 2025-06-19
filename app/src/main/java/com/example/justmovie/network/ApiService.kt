package com.example.justmovie.network

import com.example.justmovie.BuildConfig
import com.example.justmovie.model.MovieResponse
import com.example.justmovie.model.ResponseWithDates
import com.example.justmovie.model.ShowsResponse
import com.example.justmovie.model.TrendingShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    // Movies
    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Path("time_window") timeWindow: String
    ): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Query("page") page:Int?
    ): Response<ResponseWithDates>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Query("page") page:Int?
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Query("page") page:Int?
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Query("page") page:Int?
    ): Response<ResponseWithDates>

    // Shows
    @GET("trending/tv/{time_window}")
    suspend fun getTrendingShows(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Path("time_window") timeWindow: String
    ): Response<TrendingShowResponse>

    @GET("tv/airing_today")
    suspend fun getAiringToday(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Query("page") page:Int?
    ): Response<ShowsResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAir(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Query("page") page:Int?
    ): Response<ShowsResponse>

    @GET("tv/popular")
    suspend fun getPopularShows(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Query("page") page:Int?
    ): Response<ShowsResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedShows(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.ACCESS_TOKEN}",
        @Query("page") page:Int?
    ): Response<ShowsResponse>
}