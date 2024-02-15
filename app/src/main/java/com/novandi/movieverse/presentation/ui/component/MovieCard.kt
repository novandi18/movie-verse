package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.novandi.core.domain.model.Movie
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.ui.theme.rubikFamily

@Composable
fun MovieCard(
    movie: Movie,
    fullWidth: Boolean = false,
    navigateToMovie: (Int) -> Unit
) {
    Card(
        modifier = Modifier.then(
            if (fullWidth) Modifier.fillMaxWidth()
            else Modifier.width(160.dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = { navigateToMovie(movie.id) }
    ) {
        if (fullWidth) MovieCardHorizontal(movie = movie) else MovieCardVertical(movie = movie)
    }
}

@Composable
private fun MovieCardVertical(movie: Movie) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .height(140.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            model = movie.poster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.image_placeholder),
            error = painterResource(id = R.drawable.image_error)
        )
        Column(
            modifier = Modifier.height(100.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontFamily = rubikFamily
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = movie.genre,
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = rubikFamily
                )
                MovieRating(rating = movie.voteAverage)
            }
        }
    }
}

@Composable
private fun MovieCardHorizontal(movie: Movie) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            model = movie.poster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.image_placeholder),
            error = painterResource(id = R.drawable.image_error)
        )
        Column(
            modifier = Modifier.height(100.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontFamily = rubikFamily
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = movie.genre,
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = rubikFamily
                )
                MovieRating(rating = movie.voteAverage)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieCardPreview() {
    MovieVerseTheme {
        MovieCard(
            Movie(1, "Naruto Shippuden", "", "", "", 1.1, "", ""),
            navigateToMovie = {}
        )
    }
}