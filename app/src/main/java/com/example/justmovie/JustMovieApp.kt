package com.example.justmovie

import android.app.Application
/*
import com.example.justmovie.di.networkModule
import com.example.justmovie.di.repositoryModule
import com.example.justmovie.di.sourceModule
import com.example.justmovie.di.viewModelModule
*/
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@HiltAndroidApp
class JustMovieApp : Application() {

    /*override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JustMovieApp)
            androidLogger()
            modules(networkModule, repositoryModule, viewModelModule, sourceModule)
        }
    }*/
}