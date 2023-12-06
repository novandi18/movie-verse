package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCarousel(
    modifier: Modifier = Modifier,
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit
) {
    val pagerState = rememberPagerState { itemsCount }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
        ) {
            itemContent(it)
        }

        Surface(
            modifier = Modifier
                .padding(bottom = 16.dp, end = 8.dp)
                .align(Alignment.BottomEnd),
            shape = CircleShape,
            color = Color.Transparent
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 6.dp,
                selectedColor = White
            )
        }
    }
}

@Preview
@Composable
private fun MovieCarouselPreview() {
    MovieVerseTheme {
        MovieCarousel(
            itemsCount = 5
        ) {}
    }
}