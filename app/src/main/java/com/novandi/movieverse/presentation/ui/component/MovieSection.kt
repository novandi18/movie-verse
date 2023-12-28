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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White

@Composable
fun MovieSection(
    sectionName: String,
    movies: Resource<List<Movie>>,
    cardFullWidth: Boolean = false,
    navigateToMovie: (Int) -> Unit
) {
    var data by remember { mutableStateOf<List<Movie>?>(null) }
    var loading by rememberSaveable { mutableStateOf(true) }

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

        if (loading) {
            MovieCardSkeleton(fullWidth = cardFullWidth)
        } else {
            if (data != null) {
                if (data!!.isNotEmpty()) {
                    LazyRow(
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(data!!.size) { index ->
                            MovieCard(
                                movie = data!![index],
                                fullWidth = cardFullWidth,
                                navigateToMovie = navigateToMovie
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieSectionPreview() {
    MovieVerseTheme {
        MovieSection(
            "NOW PLAYING",
            Resource.Loading(),
            navigateToMovie = {}
        )
    }
}