package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.novandi.core.domain.model.Movie
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme

@Composable
fun CardCarousel(movies: List<Movie>, navigateToMovie: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .height(250.dp)
            .padding(top = 20.dp, start = 18.dp, end = 18.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Carousel(
            itemsCount = movies.size,
            itemContent = { index ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navigateToMovie(movies[index].id)
                        }.drawWithCache {
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
                    model = movies[index].poster,
                    contentDescription = movies[index].title,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.image_placeholder),
                    error = painterResource(id = R.drawable.image_error),
                )
            },
            movies = movies
        )
    }
}

@Preview
@Composable
private fun CardCarouselPreview() {
    MovieVerseTheme {
        CardCarousel(
            listOf(
                Movie(1, "Avengers", "", "", "", 1.1, "Action", ""),
                Movie(1, "2012", "", "", "", 1.1, "Action", ""),
                Movie(1, "Acid", "", "", "", 1.1, "Horror", "")
            ), {}
        )
    }
}