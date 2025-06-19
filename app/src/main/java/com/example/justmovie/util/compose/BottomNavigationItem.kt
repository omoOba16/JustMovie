package com.example.justmovie.util.compose

import com.example.justmovie.R
import com.example.justmovie.model.Screens

data class BottomNavigationItem(
    val label : String = "",
    val icon : Int = R.drawable.ic_movie,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Movies",
                icon =  R.drawable.ic_movie,
                route = Screens.Movies.route
            ),
            BottomNavigationItem(
                label = "Tv Shows",
                icon = R.drawable.ic_tv,
                route = Screens.Shows.route
            )
        )
    }
}