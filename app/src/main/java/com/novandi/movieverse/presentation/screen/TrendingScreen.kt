package com.novandi.movieverse.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.novandi.movieverse.presentation.ui.component.LoadStateError
import com.novandi.movieverse.presentation.ui.component.MovieCard
import com.novandi.movieverse.presentation.ui.component.MovieCardSkeleton
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.ui.theme.rubikFamily
import com.novandi.movieverse.presentation.viewmodel.TrendingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    viewModel: TrendingViewModel = hiltViewModel(),
    navigateToMovie: (Int) -> Unit
) {
    val movies = viewModel.trendingMovies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Trending \uD83D\uDD25",
                        color = White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                emojiSupportMatch = EmojiSupportMatch.Default
                            )
                        ),
                        textAlign = TextAlign.Center,
                        fontFamily = rubikFamily
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(movies.itemCount) { index ->
                    if (movies[index] != null) {
                        MovieCard(movie = movies[index]!!, navigateToMovie = navigateToMovie)
                    }
                }

                movies.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                repeat(5) {
                                    MovieCardSkeleton(fullWidth = true)
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = movies.loadState.refresh as LoadState.Error
                            item {
                                LoadStateError(errorMessage = error.error.localizedMessage!!) {
                                    retry()
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                repeat(5) {
                                    MovieCardSkeleton(fullWidth = true)
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = movies.loadState.refresh as LoadState.Error
                            item {
                                LoadStateError(errorMessage = error.error.localizedMessage!!) {
                                    retry()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrendingPreview() {
    MovieVerseTheme {
        TrendingScreen(
            navigateToMovie = {}
        )
    }
}