package com.example.justmovie.util.compose


import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun EndlessLazyRow(
    lazyListState: LazyListState,
    buffer: Int = 5, // Number of items from the end to start loading more
    isLoading: Boolean,
    hasMoreData: Boolean,
    loadMoreItems: () -> Unit
) {
    // True if the last visible item is within the buffer zone
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
            // Ensure there are items and we are not already at the absolute end
            if (lastVisibleItem == null || lazyListState.layoutInfo.totalItemsCount == 0) {
                false
            } else {
                lastVisibleItem.index >= lazyListState.layoutInfo.totalItemsCount - 1 - buffer
            }
        }
    }

    LaunchedEffect(shouldLoadMore.value, isLoading, hasMoreData) {
        // snapshotFlow converts the derivedState into a Flow
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged() // Only emit when the value actually changes
            .filter { it && !isLoading && hasMoreData } // Only proceed if true, not loading, and more data exists
            .collect {
                Log.d("EndlessLazyRow", "Load more items triggered")
                loadMoreItems()
            }
    }
}

@Composable
fun EndlessLazyGridScrollListener(
    lazyGridState: LazyGridState,
    buffer: Int? = null, // If you want to manually set the buffer item count
    visibleItemsThreshold: Int? = null, // Alternative way to define buffer based on visible items from end
    isLoading: Boolean,
    hasMoreData: Boolean,
    loadMoreItems: () -> Unit
) {

    val effectiveBuffer = remember(buffer, visibleItemsThreshold,
        remember { derivedStateOf { lazyGridState.layoutInfo } }) {
        buffer
            ?: if (visibleItemsThreshold != null) {
                val columnCount = lazyGridState.layoutInfo.visibleItemsInfo
                    .map { it.row }
                    .distinct()
                    .count()
                visibleItemsThreshold
            } else {
                5
            }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false // No visible items, nothing to do

            val totalItems = lazyGridState.layoutInfo.totalItemsCount
            if (totalItems == 0) return@derivedStateOf false


            // Check if the index of the last visible item is close to the total item count
            lastVisibleItem.index >= totalItems - 1 - effectiveBuffer
        }
    }

    LaunchedEffect(shouldLoadMore.value, isLoading, hasMoreData) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it && !isLoading && hasMoreData }
            .collect {
                Log.d("EndlessLazyGrid", "Load more items triggered")
                loadMoreItems()
            }
    }
}