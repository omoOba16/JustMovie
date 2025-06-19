package com.example.justmovie.ui.shows

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.justmovie.model.Screens
import com.example.justmovie.model.ShowCategory
import com.example.justmovie.ui.shows.trending.TrendingShowsState
import com.example.justmovie.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_ORIGINAL
import com.example.justmovie.util.compose.ShowsEndlessLazyRowSection
import com.example.justmovie.util.compose.getShimmerAnimateBrush
import com.example.justmovie.viewmodel.ShowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsScreen(navController: NavController, viewModel: ShowViewModel) {

    val isDark = isSystemInDarkTheme()
    val uiStateTrendingShows by viewModel.uiStateTrendingShows.collectAsStateWithLifecycle()
    val uiStateAiringTodayShows by viewModel.uiStateAiringTodayShows.collectAsStateWithLifecycle()
    val uiStateOnAirShows by viewModel.uiStateOnTheAirShows.collectAsStateWithLifecycle()
    val uiStatePopularShows by viewModel.uiStatePopularShows.collectAsStateWithLifecycle()
    val uiStateTopRatedShows by viewModel.uiStateTopRatedShows.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shows",
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
            TrendingShowsUi(uiStateTrendingShows)
            ShowsEndlessLazyRowSection(
                title = "Airing Today",
                isDark = isDark,
                shows = uiStateAiringTodayShows.shows,
                isLoading = uiStateAiringTodayShows.isLoading,
                hasMoreData = uiStateAiringTodayShows.hasMoreData,
                loadMore = {
                    viewModel.getAiringToday()
                },
                onSeeAll = {
                    navController.navigate(Screens.FullShows.createRoute(ShowCategory.AIRING_TODAY.apiValue))
                },
                onShowClick = { show ->
                }
            )
            ShowsEndlessLazyRowSection(
                title = "On The Air",
                isDark = isDark,
                shows = uiStateOnAirShows.shows,
                isLoading = uiStateOnAirShows.isLoading,
                hasMoreData = uiStateOnAirShows.hasMoreData,
                loadMore = {
                    viewModel.getOnTheAir()
                },
                onSeeAll = {
                    navController.navigate(Screens.FullShows.createRoute(ShowCategory.ON_THE_AIR.apiValue))
                },
                onShowClick = { show ->
                }
            )
            ShowsEndlessLazyRowSection(
                title = "Popular",
                isDark = isDark,
                shows = uiStatePopularShows.shows,
                isLoading = uiStatePopularShows.isLoading,
                hasMoreData = uiStatePopularShows.hasMoreData,
                loadMore = {
                    viewModel.getPopularShows()
                },
                onSeeAll = {
                    navController.navigate(Screens.FullShows.createRoute(ShowCategory.POPULAR.apiValue))
                },
                onShowClick = { show ->
                }
            )
            ShowsEndlessLazyRowSection(
                title = "Top Rated",
                isDark = isDark,
                shows = uiStateTopRatedShows.shows,
                isLoading = uiStateTopRatedShows.isLoading,
                hasMoreData = uiStateTopRatedShows.hasMoreData,
                loadMore = {
                    viewModel.getTopRatedShows()
                },
                onSeeAll = {
                    navController.navigate(Screens.FullShows.createRoute(ShowCategory.TOP_RATED.apiValue))
                },
                onShowClick = { show ->
                }
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp))
        }
    }
}

@Composable
fun TrendingShowsUi(
    state: TrendingShowsState
) {
    val shimmerAnimateBrush = getShimmerAnimateBrush()
    Box(modifier = Modifier.fillMaxWidth()){
        val shows = state.shows
        val pagerState = rememberPagerState(pageCount = {
            if(shows.isNotEmpty()) shows.size else 0
        })
        if(state.isLoading && shows.isEmpty()){
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
                            .data("${MOVIE_IMAGE_POSTER_SIZE_ORIGINAL}${shows[page]?.posterPath}")
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
                                text = shows[page]?.name ?: "",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = FontWeight(700)
                                ),
                                color = Color.White,
                                maxLines = 2
                            )
                            Text(
                                text = shows[page]?.firstAirDate ?: "",
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