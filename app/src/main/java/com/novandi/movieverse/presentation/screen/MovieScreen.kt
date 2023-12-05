package com.novandi.movieverse.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme

@Composable
fun MovieScreen(
    movieId: Int
) {
    Text(text = "Movie Screen $movieId")
}

@Preview(showBackground = true)
@Composable
private fun MoviePreview() {
    MovieVerseTheme {
        MovieScreen(1)
    }
}