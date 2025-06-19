package com.example.justmovie.data.repositories

import com.example.justmovie.data.source.RemoteDataSource
import com.example.justmovie.model.MovieResponse
import com.example.justmovie.model.ResponseWithDates
import com.example.justmovie.model.UiState
import com.example.justmovie.util.toResultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getTrendingMovies(timeWindow: String): Flow<UiState<MovieResponse?>> {
        return toResultFlow {
            remoteDataSource.getTrendingMovies(timeWindow)
        }
    }

    suspend fun getNowPlayingMovies(page:Int?): Flow<UiState<ResponseWithDates?>> {
        return toResultFlow {
            remoteDataSource.getNowPlayingMovies(page)
        }
    }

    suspend fun getPopularMovies(page:Int?): Flow<UiState<MovieResponse?>> {
        return toResultFlow {
            remoteDataSource.getPopularMovies(page)
        }
    }

    suspend fun getTopRatedMovies(page:Int?): Flow<UiState<MovieResponse?>> {
        return toResultFlow{
            remoteDataSource.getTopRatedMovies(page)
        }
    }

    suspend fun getUpcomingMovies(page:Int?): Flow<UiState<ResponseWithDates?>> {
        return toResultFlow {
            remoteDataSource.getUpcomingMovies(page)
        }
    }
}