package com.example.justmovie.util.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.justmovie.model.Show

@Composable
fun ShowsEndlessLazyRowSection(
    modifier: Modifier = Modifier,
    title: String = "",
    isDark: Boolean = false,
    isLoading: Boolean = false,
    hasMoreData: Boolean = false,
    shows: List<Show?> = emptyList(),
    loadMore: () -> Unit = {},
    onSeeAll: () -> Unit = {},
    onShowClick: (show: Show?) -> Unit = {}
){
    val state = rememberLazyListState()
    val shimmerAnimateBrush = getShimmerAnimateBrush()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp),
                style = TextStyle(
                    color = if(isDark) Color.White else Color.Black,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight(700)
                )
            )

            IconButton(
                onClick = { onSeeAll() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
        }

        EndlessLazyRow(
            lazyListState = state,
            isLoading = isLoading,
            hasMoreData = hasMoreData,
            loadMoreItems = {
                loadMore()
            }
        )

        LazyRow(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            items(
                count = shows.size,
                key = { index -> shows[index]?.id ?: index }
            ) { index ->

                ResourceCellUi(
                    imagePath = shows[index]?.posterPath ?: "",
                    title = shows[index]?.name ?: "",
                    onClick = { onShowClick(shows[index]) },
                    modifier = Modifier.aspectRatio(2f / 3f)
                )
            }

            if(isLoading && shows.isEmpty()){
                items(8){
                    Spacer(modifier = Modifier
                        .width(150.dp)
                        .aspectRatio(2f / 3f)
                        .background(shimmerAnimateBrush, RoundedCornerShape(10.dp))
                    )
                }
            }
            if(isLoading && shows.isNotEmpty()){
                items(8){
                    Spacer(modifier = Modifier
                        .width(150.dp)
                        .aspectRatio(2f / 3f)
                        .background(shimmerAnimateBrush, RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    }
}