package com.novandi.movieverse.presentation.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Comment
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.novandi.core.data.response.Resource
import com.novandi.core.data.source.remote.response.FavoriteRequest
import com.novandi.core.data.source.remote.response.WatchlistRequest
import com.novandi.core.domain.model.MovieDetail
import com.novandi.core.domain.model.MovieDetailImages
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.ui.component.MovieBottomSheet
import com.novandi.movieverse.presentation.ui.component.MovieCarousel
import com.novandi.movieverse.presentation.ui.component.MovieDetailSkeleton
import com.novandi.movieverse.presentation.ui.component.MovieRating
import com.novandi.movieverse.presentation.ui.component.MovieSection
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.viewmodel.MovieViewModel
import com.novandi.core.utils.formatDate
import com.novandi.core.utils.toImageUrlOriginal
import com.novandi.movieverse.presentation.ui.component.RatingChanger
import com.novandi.movieverse.presentation.ui.component.RatingSkeleton
import com.novandi.movieverse.presentation.ui.theme.Red40
import com.novandi.movieverse.presentation.ui.theme.rubikFamily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    movieId: Int,
    navigateBack: () -> Unit,
    navigateToMovie: (Int) -> Unit
) {
    val movieReviews = viewModel.movieReviews.collectAsLazyPagingItems()
    var topBarVisibility by rememberSaveable { mutableStateOf(false) }
    val movieImages = viewModel.getMovieImages(movieId)
    val scrollState = rememberScrollState()
    var movieData by remember { mutableStateOf<MovieDetail?>(null) }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var showReview by remember { mutableStateOf(false) }
    val similarMovies by viewModel.similarMovies.observeAsState(initial = Resource.Loading())
    val accountId by viewModel.accountId.observeAsState()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val isWatchlist by viewModel.isWatchlist.collectAsStateWithLifecycle()
    val updateFavorite by viewModel.updateFavorite.collectAsStateWithLifecycle()
    val updateWatchlist by viewModel.updateWatchlist.collectAsStateWithLifecycle()
    val ratingMovie by viewModel.ratingMovie.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (scrollState.isScrollInProgress) {
        topBarVisibility = scrollState.value >= 400
    }

    LaunchedEffect(true) {
        viewModel.getMovieReviews(movieId)
        viewModel.getSimilarMovies(movieId)
        viewModel.getMovieRating(accountId!!.toInt(), viewModel.ratingPage, movieId)
        if (accountId!!.isNotEmpty()) {
            viewModel.getIsFavorite(accountId!!.toInt(), movieId)
            viewModel.getIsWatchlist(accountId!!.toInt(), movieId)
        }
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

    LaunchedEffect(isFavorite is Resource.Loading) {
        when (isFavorite) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                viewModel.onFavoriteChange(isFavorite?.data!!)
            }
            is Resource.Error -> {}
            else -> {}
        }
    }

    LaunchedEffect(isWatchlist is Resource.Loading) {
        when (isWatchlist) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                viewModel.onWatchlistChange(isWatchlist?.data!!)
            }
            is Resource.Error -> {}
            else -> {}
        }
    }

    LaunchedEffect(updateFavorite is Resource.Loading) {
        when (updateFavorite) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                if (updateFavorite!!.data != null) {
                    if (updateFavorite!!.data?.success == true) {
                        viewModel.onFavoriteChange(!viewModel.favorite)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(
                                    if (viewModel.favorite)
                                        R.string.success_add_favorite else
                                        R.string.success_remove_favorite
                                ),
                                withDismissAction = true
                            )
                        }
                    }
                }
            }
            is Resource.Error -> {}
            else -> {}
        }
    }

    LaunchedEffect(updateWatchlist is Resource.Loading) {
        when (updateWatchlist) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                if (updateWatchlist!!.data != null) {
                    if (updateWatchlist!!.data?.success == true) {
                        viewModel.onWatchlistChange(!viewModel.watchlist)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(
                                    if (viewModel.watchlist)
                                        R.string.success_add_watchlist else
                                        R.string.success_remove_watchlist
                                ),
                                withDismissAction = true
                            )
                        }
                    }
                }
            }
            is Resource.Error -> {}
            else -> {}
        }
    }

    LaunchedEffect(ratingMovie is Resource.Loading) {
        when (ratingMovie) {
            is Resource.Loading -> viewModel.onRatingLoadingChange(true)
            is Resource.Success -> {
                if (ratingMovie?.data != null) {
                    if (ratingMovie?.data!!.results.isNotEmpty()) {
                        if (ratingMovie?.data!!.results[0].rating != null) {
                            viewModel.onRatingLoadingChange(false)
                            viewModel.onRatingChange(ratingMovie?.data!!.results[0].rating!!.toInt())
                        }
                    } else if (viewModel.ratingPage < ratingMovie?.data!!.totalPages) {
                        viewModel.onRatingPageChange(viewModel.ratingPage + 1)
                        viewModel.getMovieRating(accountId!!.toInt(), viewModel.ratingPage, movieId)
                    } else {
                        viewModel.onRatingLoadingChange(false)
                    }
                }
            }
            is Resource.Error -> {
                viewModel.onRatingLoadingChange(false)
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    MovieContent(
                        movie = movieData,
                        totalReview = movieReviews.itemCount,
                        onReviewClick = { isShowing ->
                            showReview = isShowing
                        },
                        onRatingChange = { viewModel.onRatingChange(it) },
                        rating = viewModel.rating,
                        ratingLoading = viewModel.ratingLoading
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp))
                MovieSection(
                    sectionName = stringResource(id = R.string.similar_movies),
                    movies = similarMovies,
                    navigateToMovie = navigateToMovie
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp))
            }

            if (showReview) {
                MovieBottomSheet(
                    showReview = { isShowing -> showReview = isShowing },
                    sheetState = sheetState,
                    reviewData = movieReviews
                )
            }

            TopAppBar(
                title = {
                    AnimatedVisibility(visible = topBarVisibility) {
                        Text(
                            text = movieData?.title ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = rubikFamily
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.clip(CircleShape),
                        onClick = { navigateBack() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Black.copy(.7f),
                            contentColor = White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (topBarVisibility) Black else Color.Transparent
                ),
                actions = {
                    if (accountId != null && accountId!!.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                val request = FavoriteRequest(id = movieId, isFavorite = !viewModel.favorite)
                                viewModel.updateFavorite(accountId!!.toInt(), request)
                            }
                        ) {
                            Icon(
                                imageVector = if (viewModel.favorite) Icons.Rounded.Favorite else
                                    Icons.Rounded.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.favorite),
                                tint = if (viewModel.favorite) Red40 else White
                            )
                        }
                        IconButton(
                            onClick = {
                                val request = WatchlistRequest(id = movieId, isWatchlist = !viewModel.watchlist)
                                viewModel.updateWatchlist(accountId!!.toInt(), request)
                            }
                        ) {
                            Icon(
                                imageVector = if (viewModel.watchlist) Icons.Rounded.Bookmark else
                                    Icons.Rounded.BookmarkBorder,
                                contentDescription = stringResource(id = R.string.watchlist),
                                tint = if (viewModel.watchlist) Color.Yellow else White
                            )
                        }
                    }
                }
            )
        }
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
            if (movieImagesData != null) {
                if (movieImagesData!!.isNotEmpty()) {
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
                } else {
                    Image(
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
                        painter = painterResource(id = R.drawable.no_poster_available),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

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
                    fontWeight = FontWeight.Medium,
                    fontFamily = rubikFamily
                )
                Text(
                    text = movie.genre,
                    color = White,
                    fontSize = 16.sp,
                    fontFamily = rubikFamily
                )
                MovieRating(rating = movie.voteAverage)
            }
        }
    }
}

@Composable
private fun MovieContent(
    movie: MovieDetail?,
    totalReview: Int,
    onReviewClick: (Boolean) -> Unit,
    onRatingChange: (Int) -> Unit,
    rating: Int = 0,
    ratingLoading: Boolean = false
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
                contentDescription = stringResource(id = R.string.release_date),
                tint = White
            )
            Text(
                text = movie?.releaseDate.toString().formatDate(),
                color = White,
                fontSize = 14.sp,
                fontFamily = rubikFamily
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
                contentDescription = stringResource(id = R.string.popularity),
                tint = White
            )
            Text(
                text = movie?.voteCount.toString(),
                color = White,
                fontSize = 14.sp,
                fontFamily = rubikFamily
            )
        }
        Row(
            modifier = Modifier
                .background(Gray.copy(.2f), RoundedCornerShape(8.dp))
                .clickable {
                    onReviewClick(true)
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Comment,
                contentDescription = stringResource(id = R.string.reviews_title),
                tint = White
            )
            if (totalReview > 0) {
                Text(
                    text = totalReview.toString(),
                    color = White,
                    fontSize = 14.sp,
                    fontFamily = rubikFamily
                )
            }
        }
    }

    if (ratingLoading) {
        RatingSkeleton()
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.rate_this_movie),
                color = Gray,
                fontFamily = rubikFamily
            )
            RatingChanger(
                onRateChange = onRatingChange,
                rate = rating
            )
        }
    }

    HorizontalDivider(
        color = Gray.copy(.2f)
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.overview),
            color = Gray,
            fontFamily = rubikFamily
        )
        Text(
            text = if (movie?.overview.toString() != "") movie?.overview.toString()
            else stringResource(id = R.string.no_overview),
            color = White,
            fontFamily = rubikFamily
        )
    }

    HorizontalDivider(
        color = Gray.copy(.2f)
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.tagline),
            color = Gray,
            fontFamily = rubikFamily
        )
        Text(
            text = if (movie?.tagline.toString() != "") movie?.tagline.toString()
            else stringResource(id = R.string.no_tagline),
            color = White,
            fontFamily = rubikFamily
        )
    }

    HorizontalDivider(
        color = Gray.copy(.2f)
    )
}

@Preview(showBackground = true)
@Composable
private fun MoviePreview() {
    MovieVerseTheme {
        MovieScreen(movieId = 1, navigateBack = {}, navigateToMovie = {})
    }
}