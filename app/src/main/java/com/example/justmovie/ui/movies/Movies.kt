package com.example.justmovie.ui.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.justmovie.model.Screens
import com.example.justmovie.ui.movies.trending.TrendingMoviesState
import com.example.justmovie.ui.theme.JustMovieTheme
import com.example.justmovie.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_ORIGINAL
import com.example.justmovie.util.compose.getShimmerAnimateBrush
import com.example.justmovie.viewmodel.MovieViewModel
import kotlin.collections.isNotEmpty
import androidx.compose.ui.text.font.FontWeight
import com.example.justmovie.model.MovieCategory
import com.example.justmovie.util.compose.MoviesEndlessLazyRowSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(navController: NavController, viewModel: MovieViewModel) {

    val isDark = isSystemInDarkTheme()
    val uiStateTrendingMovie by viewModel.uiStateTrendingMovies.collectAsStateWithLifecycle()
    val uiStateNowPlayingMovie by viewModel.uiStateNowPlayingMovies.collectAsStateWithLifecycle()
    val uiStatePopularMovie by viewModel.uiStatePopularMovies.collectAsStateWithLifecycle()
    val uiStateTopRatedMovie by viewModel.uiStateTopRatedMovies.collectAsStateWithLifecycle()
    val uiStateUpcomingMovie by viewModel.uiStateUpcomingMovies.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movies",
                        style = TextStyle(
                            color = if(isDark) Color.White else Color.Black,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight(700)
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TrendingMoviesUi(uiStateTrendingMovie)
            MoviesEndlessLazyRowSection(
                title = "Now Playing",
                isDark = isDark,
                movies = uiStateNowPlayingMovie.movies,
                isLoading = uiStateNowPlayingMovie.isLoading,
                hasMoreData = uiStateNowPlayingMovie.hasMoreData,
                loadMore = {
                    viewModel.getNowPlayingMovies()
                },
                onSeeAll = {
                    navController.navigate(Screens.FullMovies.createRoute(MovieCategory.NOW_PLAYING.apiValue))
                },
                onMovieClick = { movie ->}
            )
            MoviesEndlessLazyRowSection(
                title = "Popular",
                isDark = isDark,
                movies = uiStatePopularMovie.movies,
                isLoading = uiStatePopularMovie.isLoading,
                hasMoreData = uiStatePopularMovie.hasMoreData,
                loadMore = {
                    viewModel.getPopularMovies()
                },
                onSeeAll = {
                    navController.navigate(Screens.FullMovies.createRoute(MovieCategory.POPULAR.apiValue))
                },
                onMovieClick = { movie ->}
            )
            MoviesEndlessLazyRowSection(
                title = "Top Rated",
                isDark = isDark,
                movies = uiStateTopRatedMovie.movies,
                isLoading = uiStateTopRatedMovie.isLoading,
                hasMoreData = uiStateTopRatedMovie.hasMoreData,
                loadMore = {
                    viewModel.getTopRatedMovies()
                },
                onSeeAll = {
                    navController.navigate(Screens.FullMovies.createRoute(MovieCategory.TOP_RATED.apiValue))
                },
                onMovieClick = { movie ->}
            )
            MoviesEndlessLazyRowSection(
                title = "Upcoming",
                isDark = isDark,
                movies = uiStateUpcomingMovie.movies,
                isLoading = uiStateUpcomingMovie.isLoading,
                hasMoreData = uiStateUpcomingMovie.hasMoreData,
                loadMore = {
                    viewModel.getUpcomingMovies()
                },
                onSeeAll = {
                    navController.navigate(Screens.FullMovies.createRoute(MovieCategory.UPCOMING.apiValue))
                },
                onMovieClick = { movie ->}
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp))
        }
    }
}

@Composable
fun TrendingMoviesUi(
    state: TrendingMoviesState
) {
    val shimmerAnimateBrush = getShimmerAnimateBrush()
    Box(modifier = Modifier.fillMaxWidth()){
        val movies = state.trendingMovies
        val pagerState = rememberPagerState(pageCount = {
            if(movies.isNotEmpty()) movies.size else 0
        })
        if(state.isLoading && movies.isEmpty()){
            Spacer(
                modifier = Modifier
                    .padding(16.dp)
                    .aspectRatio(1.586f)
                    .background(shimmerAnimateBrush, RoundedCornerShape(10.dp))
            )
        } else {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 16.dp,
                contentPadding = PaddingValues(horizontal = 30.dp)
            ) { page ->
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                ){
                    AsyncImage(
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data("${MOVIE_IMAGE_POSTER_SIZE_ORIGINAL}${movies[page]?.posterPath}")
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        modifier = Modifier.aspectRatio(1.586f),
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                )
                            )
                        )
                        .align(Alignment.BottomCenter)
                    ){
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = movies[page]?.title ?: "",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = FontWeight(700)
                                ),
                                color = Color.White,
                                maxLines = 2
                            )
                            Text(
                                text = movies[page]?.releaseDate ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
@Preview
fun ComposeSandbox(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))

            var input by remember { mutableStateOf("") }
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("textField")
            )

            Button(
                onClick = { input = "" },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("clearBtn"),
            ){
                Text("Clear")
            }

            Text(
                text = input,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("displayInput")
            )
        }
    }
}

@Composable
@Preview
fun MoviesScreenPreview() {
    /*val movies = listOf(
        Movie(
            overview = "Overview 1",
            originalLanguage = "Original Language 1",
            originalTitle = "Original Title 1",
            posterPath = "https://miro.medium.com/v2/resize:fit:1400/1*vNRJpp2GlbNh0fAawqruFw.jpeg"

        ),
        Movie(
            overview = "Overview 1",
            originalLanguage = "Original Language 1",
            originalTitle = "Original Title 1",
            posterPath = "https://miro.medium.com/v2/resize:fit:1400/1*vNRJpp2GlbNh0fAawqruFw.jpeg"
        )
    )*/

    
    JustMovieTheme {
        /*MovieContents(
            innerPadding = PaddingValues(0.dp),
            movies = movies,
            dataState = MovieViewModel.DataState.IDLE
        )*/
    }
}