package com.example.justmovie.ui.movies

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.justmovie.viewmodel.MovieViewModel

@Composable
fun DetailsScreen(movieId: String, navController: NavController, viewModel: MovieViewModel){

    LaunchedEffect(Unit){
        Log.d("DetailsScreen", "Movie ID: $movieId")
        viewModel.getNowPlayingMovies()
    }
}