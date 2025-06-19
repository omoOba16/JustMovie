package com.example.justmovie.data.repositories

import com.example.justmovie.data.source.RemoteDataSource
import com.example.justmovie.model.ShowsResponse
import com.example.justmovie.model.TrendingShowResponse
import com.example.justmovie.model.UiState
import com.example.justmovie.util.toResultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getTrendingShows(timeWindow: String): Flow<UiState<TrendingShowResponse?>> {
        return toResultFlow {
            remoteDataSource.getTrendingShows(timeWindow)
        }
    }

    suspend fun getAiringToday(page:Int?): Flow<UiState<ShowsResponse?>> {
        return toResultFlow {
            remoteDataSource.getAiringToday(page)
        }
    }

    suspend fun getOnTheAir(page:Int?): Flow<UiState<ShowsResponse?>> {
        return toResultFlow {
            remoteDataSource.getOnTheAir(page)
        }
    }

    suspend fun getPopularShows(page:Int?): Flow<UiState<ShowsResponse?>> {
        return toResultFlow{
            remoteDataSource.getPopularShows(page)
        }
    }

    suspend fun getTopRatedShows(page:Int?): Flow<UiState<ShowsResponse?>> {
        return toResultFlow {
            remoteDataSource.getTopRatedShows(page)
        }
    }
}