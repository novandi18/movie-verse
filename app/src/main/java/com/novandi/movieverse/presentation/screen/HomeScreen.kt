package com.novandi.movieverse.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.novandi.movieverse.R
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.presentation.ui.component.CardCarousel
import com.novandi.movieverse.presentation.ui.component.MovieSection
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.Red80
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val discoverMovies by viewModel.discoverMovies.observeAsState(initial = Resource.Loading())
    val nowPlayingMovies by viewModel.nowPlayingMovies.observeAsState(initial = Resource.Loading())
    val upcomingMovies by viewModel.upcomingMovies.observeAsState(initial = Resource.Loading())
    val topRatedMovies by viewModel.topRatedMovies.observeAsState(initial = Resource.Loading())

    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(Red80.copy(.3f), Black),
                center = size.center,
                radius = biggerDimension / 2f,
                colorStops = listOf(0f, 0.95f)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(largeRadialGradient)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp, top = 32.dp),
            text = stringResource(id = R.string.discover),
            color = White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        CarouselContent(movies = discoverMovies)
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        MovieSection(sectionName = stringResource(id = R.string.now_playing), nowPlayingMovies)
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        MovieSection(sectionName = stringResource(id = R.string.upcoming), upcomingMovies)
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        MovieSection(sectionName = stringResource(id = R.string.top_rated), topRatedMovies)
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Composable
private fun CarouselContent(movies: Resource<List<Movie>>) {
    var data by remember { mutableStateOf<List<Movie>?>(null) }
    var loading by rememberSaveable { mutableStateOf(false) }

    when (movies) {
        is Resource.Loading -> loading = true
        is Resource.Success -> {
            loading = false
            data = movies.data
        }
        is Resource.Error -> {
            loading = false
            data = listOf()
        }
    }

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 20.dp, start = 18.dp, end = 18.dp)
                .background(color = Gray, shape = RoundedCornerShape(16.dp))
        )
    } else {
        if (data != null) {
            CardCarousel(movies = data!!)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MovieVerseTheme {
        HomeScreen()
    }
}