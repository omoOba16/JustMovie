package com.example.justmovie.ui.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.justmovie.model.Movie
import com.example.justmovie.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_W500
import com.example.justmovie.util.compose.EndlessLazyGridScrollListener
import com.example.justmovie.util.compose.getShimmerAnimateBrush
import com.example.justmovie.viewmodel.FullMoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullMoviesScreen(navController: NavController, viewModel: FullMoviesViewModel){

    val isDark = isSystemInDarkTheme()
    val uiState by viewModel.uiStateMovies.collectAsStateWithLifecycle()
    val lazyGridState = rememberLazyGridState()

    EndlessLazyGridScrollListener(
        lazyGridState = lazyGridState,
        isLoading = uiState.isLoading,
        hasMoreData = uiState.hasMoreData,
        loadMoreItems = {
            viewModel.getMovies()
        },
        visibleItemsThreshold = 6
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                        style = TextStyle(
                            color = if(isDark) Color.White else Color.Black,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight(700)
                        )
                    )
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
        content = { innerPadding ->
            val shimmerAnimateBrush = getShimmerAnimateBrush()
            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = uiState.movies){ item ->
                    MovieUi(
                        movie = item,
                        onMovieClick = { movieId ->},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
                if(uiState.isLoading && uiState.movies.isEmpty()){
                    items(
                        count = 12,
                    ) {
                        MovieUi(
                            movie = null,
                            isSkeleton = true,
                            shimmerAnimateBrush = shimmerAnimateBrush,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                    }
                }

                if(uiState.isLoading && uiState.movies.isNotEmpty()){
                    items(
                        count = 12,
                    ) {
                        MovieUi(
                            movie = null,
                            isSkeleton = true,
                            shimmerAnimateBrush = shimmerAnimateBrush,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun MovieUi(
    movie: Movie?,
    isSkeleton: Boolean = false,
    shimmerAnimateBrush: Brush? = null,
    onMovieClick: ((movieId: Int?) -> Unit)? = null,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.clickable {
            onMovieClick?.invoke(movie?.id)
        }
    ) {
        if(isSkeleton && shimmerAnimateBrush != null){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(shimmerAnimateBrush))
        } else {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data("${MOVIE_IMAGE_POSTER_SIZE_W500}${movie?.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}