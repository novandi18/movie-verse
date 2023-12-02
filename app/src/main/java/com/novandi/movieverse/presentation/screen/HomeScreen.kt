package com.novandi.movieverse.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.ui.component.Carousel
import com.novandi.movieverse.presentation.ui.component.MovieSection
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.Red80
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val movies = listOf(
        "https://i.pinimg.com/originals/66/bd/9b/66bd9b1f8354a9aa6900dee35bc2a911.jpg",
        "https://japanesemusicid.com/wp-content/uploads/2018/11/I-Want-to-Eat-Your-Pancreas-Encore-Films.jpg",
        "https://static.wikia.nocookie.net/disasterfilm/images/5/51/2012_poster.jpg"
    )

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

        Card(
            modifier = Modifier
                .height(200.dp)
                .padding(top = 20.dp, start = 18.dp, end = 18.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Carousel(
                itemsCount = movies.size,
                itemContent = { index ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { },
                        model = movies[index],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.image_placeholder),
                        error = painterResource(id = R.drawable.image_error)
                    )
                }
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        MovieSection(sectionName = stringResource(id = R.string.now_playing))
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        MovieSection(sectionName = stringResource(id = R.string.upcoming))
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        MovieSection(sectionName = stringResource(id = R.string.top_rated))
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MovieVerseTheme {
        HomeScreen()
    }
}