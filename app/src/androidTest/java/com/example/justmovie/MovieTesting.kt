package com.example.justmovie

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.example.justmovie.ui.movies.MovieState
import com.example.justmovie.ui.movies.MoviesUi
import com.example.justmovie.ui.theme.JustMovieTheme
import org.junit.Rule
import org.junit.Test

class MovieTesting {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun initRender(){
        testRule.setContent {
            JustMovieTheme {
                MoviesUi(
                    uiState = MovieState(isLoading = true),
                    innerPadding = PaddingValues(0.dp),
                    lazyGridState = rememberLazyGridState()
                )
            }
        }

        testRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }
}