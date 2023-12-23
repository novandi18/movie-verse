package com.novandi.movieverse.presentation.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Comment
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.novandi.movieverse.R
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.MovieDetail
import com.novandi.movieverse.domain.model.MovieDetailImages
import com.novandi.movieverse.presentation.ui.component.MovieCarousel
import com.novandi.movieverse.presentation.ui.component.MovieDetailSkeleton
import com.novandi.movieverse.presentation.ui.component.MovieRating
import com.novandi.movieverse.presentation.ui.component.MovieSection
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.viewmodel.MovieViewModel
import com.novandi.movieverse.utils.formatDate
import com.novandi.movieverse.utils.toImageUrlOriginal
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    movieId: Int,
    navigateBack: () -> Unit
) {
    val movieImages = viewModel.getMovieImages(movieId)
    val scrollState = rememberScrollState()
    var topBarVisibility by rememberSaveable { mutableStateOf(false) }
    var movieData by remember { mutableStateOf<MovieDetail?>(null) }
    val context = LocalContext.current

    if (scrollState.isScrollInProgress) {
        topBarVisibility = scrollState.value >= 400
    }

    LaunchedEffect(true) {
        viewModel.getMovie(movieId).collect { uiState ->
            when (uiState) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    movieData = uiState.data
                }
                is Resource.Error -> {
                    Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (movieData == null) {
                MovieDetailSkeleton()
            } else {
                MovieImagesContent(movieImages, movieData!!)
                MovieContent(movieData)
            }
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            MovieSection(
                sectionName = "Recommended for you",
                movies = Resource.Loading(),
                navigateToMovie = {}
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            MovieSection(
                sectionName = "Similar Movies",
                movies = Resource.Loading(),
                navigateToMovie = {}
            )
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
        }

        TopAppBar(
            title = {
                AnimatedVisibility(visible = topBarVisibility) {
                    Text(text = movieData?.title ?: "")
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = { navigateBack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = if (topBarVisibility) Black else Color.Transparent
            )
        )
    }
}

@Composable
private fun MovieImagesContent(
    movieImages: Flow<Resource<List<MovieDetailImages>>>,
    movie: MovieDetail
) {
    var movieImagesData by remember { mutableStateOf<List<MovieDetailImages>?>(null) }
    var loading by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(true) {
        movieImages.collect { uiState ->
            when (uiState) {
                is Resource.Loading -> loading = true
                is Resource.Success -> {
                    loading = false
                    movieImagesData = uiState.data
                }
                is Resource.Error -> {
                    loading = false
                    Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier.height(250.dp)
    ) {
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Gray)
            )
        } else {
            MovieCarousel(
                itemsCount = movieImagesData!!.size,
                itemContent = { index ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(
                                        Brush.verticalGradient(
                                            0f to Black.copy(0f),
                                            1.4f to Black.copy(1f)
                                        )
                                    )
                                }
                            },
                        model = movieImagesData!![index].filePath.toImageUrlOriginal(),
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.image_placeholder),
                        error = painterResource(id = R.drawable.image_error),
                        contentScale = ContentScale.Crop
                    )
                }
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = movie.title,
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = movie.genre,
                    color = White,
                    fontSize = 16.sp
                )
                MovieRating(rating = movie.voteAverage)
            }
        }
    }
}

@Composable
private fun MovieContent(
    movie: MovieDetail?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Row(
            modifier = Modifier
                .background(Gray.copy(.2f), RoundedCornerShape(8.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.CalendarMonth,
                contentDescription = null,
                tint = White
            )
            Text(
                text = movie?.releaseDate.toString().formatDate(),
                color = White,
                fontSize = 14.sp
            )
        }
        Row(
            modifier = Modifier
                .background(Gray.copy(.2f), RoundedCornerShape(8.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Groups,
                contentDescription = null,
                tint = White
            )
            Text(
                text = movie?.voteCount.toString(),
                color = White,
                fontSize = 14.sp
            )
        }
        Row(
            modifier = Modifier
                .background(Gray.copy(.2f), RoundedCornerShape(8.dp))
                .clickable { }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Comment,
                contentDescription = null,
                tint = White
            )
            Text(
                text = "2.8k",
                color = White,
                fontSize = 14.sp
            )
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Overview",
            color = Gray
        )
        Text(
            text = movie?.overview.toString(),
            color = White
        )
    }

    Divider(
        color = Gray.copy(.2f)
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Tagline",
            color = Gray
        )
        Text(
            text = movie?.tagline.toString(),
            color = White
        )
    }

    Divider(
        color = Gray.copy(.2f)
    )
}

@Preview(showBackground = true)
@Composable
private fun MoviePreview() {
    MovieVerseTheme {
        MovieScreen(movieId = 1, navigateBack = {})
    }
}