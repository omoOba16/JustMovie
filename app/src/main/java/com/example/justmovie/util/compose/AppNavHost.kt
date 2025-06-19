package com.example.justmovie.util.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.justmovie.model.Screens
import com.example.justmovie.ui.movies.DetailsScreen
import com.example.justmovie.ui.movies.FullMoviesScreen
import com.example.justmovie.ui.movies.MoviesScreen
import com.example.justmovie.ui.shows.FullShowsScreen
import com.example.justmovie.ui.shows.ShowsScreen
import com.example.justmovie.viewmodel.FullMoviesViewModel
import com.example.justmovie.viewmodel.FullShowsViewModel
import com.example.justmovie.viewmodel.MovieViewModel
import com.example.justmovie.viewmodel.ShowViewModel

@Composable
fun AppNavHost(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Movies.route) {
        composable(Screens.Movies.route) {
            val movieViewModel = hiltViewModel<MovieViewModel>()
            MoviesScreen(navController, movieViewModel)
        }
        composable(
            route = Screens.FullMovies.route,
            arguments = listOf(
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) {
            val fullMoviesViewModel = hiltViewModel<FullMoviesViewModel>()
            FullMoviesScreen(
                navController, fullMoviesViewModel
            )
        }
        composable(
            route = Screens.Details.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val movieViewModel = hiltViewModel<MovieViewModel>()
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            DetailsScreen(
                movieId,
                navController,
                movieViewModel
            )
        }
        composable(Screens.Shows.route) {
            val showViewModel = hiltViewModel<ShowViewModel>()
            ShowsScreen(
                navController, showViewModel
            )
        }
        composable(
            route = Screens.FullShows.route,
            arguments = listOf(
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) {
            val fullShowsViewModel = hiltViewModel<FullShowsViewModel>()
            FullShowsScreen(
                navController, fullShowsViewModel
            )
        }
    }
}