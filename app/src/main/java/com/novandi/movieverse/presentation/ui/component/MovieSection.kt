package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White

@Composable
fun MovieSection(sectionName: String) {
    val movies = listOf(
        "https://i.pinimg.com/originals/66/bd/9b/66bd9b1f8354a9aa6900dee35bc2a911.jpg",
        "https://japanesemusicid.com/wp-content/uploads/2018/11/I-Want-to-Eat-Your-Pancreas-Encore-Films.jpg",
        "https://static.wikia.nocookie.net/disasterfilm/images/5/51/2012_poster.jpg"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 18.dp),
            text = sectionName,
            color = White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            state = rememberLazyListState(),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(movies.size) {
                MovieCard(image = movies[it])
            }
        }
    }
}

@Preview
@Composable
private fun MovieSectionPreview() {
    MovieVerseTheme {
        MovieSection("NOW PLAYING")
    }
}