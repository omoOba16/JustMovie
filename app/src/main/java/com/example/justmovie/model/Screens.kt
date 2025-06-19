package com.example.justmovie.model


sealed class Screens(val route : String) {
    data object Movies : Screens("movies_screen")
    object FullMovies : Screens("full_movies_screen/{categoryName}"){
        fun createRoute(categoryName: String) = "full_movies_screen/$categoryName"
    }
    data object Shows : Screens("shows_screen")
    object FullShows : Screens("full_shows_screen/{categoryName}"){
        fun createRoute(categoryName: String) = "full_shows_screen/$categoryName"
    }
    data object Details : Screens("movie_details_screen/{movieId}"){
        fun passMovieId(movieId: Int) = "movie_details_screen/$movieId"
    }

    companion object {
        val bottomBarRoutes = listOf(Movies.route, Shows.route)
    }
}

enum class MovieCategory(val apiValue: String, val title: String) {
    NOW_PLAYING("now_playing_movies", "Now Playing"),
    POPULAR("popular_movies", "Popular Movies"),
    TOP_RATED("top_rated_movies", "Top Rated Movies"),
    UPCOMING("upcoming_movies", "Upcoming Movies");

    companion object {
        fun fromApiValue(value: String): MovieCategory? = entries.find { it.apiValue == value }
    }
}

enum class ShowCategory(val apiValue: String, val title: String) {
    AIRING_TODAY("airing_today", "Airing Today"),
    ON_THE_AIR("on_the_air", "On The Air"),
    POPULAR("popular_shows", "Popular Shows"),
    TOP_RATED("top_rated_shows", "Top Rated Shows");

    companion object {
        fun fromApiValue(value: String): ShowCategory? = entries.find { it.apiValue == value }
    }
}


