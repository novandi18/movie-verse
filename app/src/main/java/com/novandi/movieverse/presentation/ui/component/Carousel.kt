package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novandi.core.domain.model.Movie
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.ui.theme.rubikFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
    movies: List<Movie>
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

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Text(
                text = movies[pagerState.currentPage].title,
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = rubikFamily
            )
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = movies[pagerState.currentPage].genre,
                color = White,
                fontSize = 16.sp,
                fontFamily = rubikFamily
            )
            MovieRating(rating = movies[pagerState.currentPage].voteAverage)
        }

        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Transparent
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 6.dp
            )
        }
    }
}

@Preview
@Composable
private fun CarouselPreview() {
    MovieVerseTheme {
        Carousel(
            itemsCount = 20,
            movies = listOf(
                Movie(1, "Avengers", "", "", "", 1.1, "Action", ""),
                Movie(1, "2012", "", "", "", 1.1, "Action", ""),
                Movie(1, "Acid", "", "", "", 1.1, "Horror", "")
            ),
            itemContent = {}
        )
    }
}