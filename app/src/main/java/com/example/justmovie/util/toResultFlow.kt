package com.example.justmovie.util

import android.util.Log
import com.example.justmovie.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.net.SocketTimeoutException

inline fun <reified T> toResultFlow(crossinline call: suspend () -> Response<T>?): Flow<UiState<T?>> {
    return flow {
        emit(UiState.Loading)
        try {
            val c = call()
            if (c?.isSuccessful == true && c.body() != null) {
                Log.d("TAG", "toResultFlow: Success" + c.body())
                emit(UiState.Success(c.body()))
            } else {
                if (c != null) {
                    emit(UiState.Error(c.message()))
                    Log.d("TAG", "toResultFlow: Error" + c.message())
                }
            }
        } catch (e: Exception) {
            Log.d("TAG", "toResultFlow: Exception" + e)
            if(e is SocketTimeoutException){
                emit(UiState.Error("Timeout Error"))
            } else {
                emit(UiState.Error(e.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)
}