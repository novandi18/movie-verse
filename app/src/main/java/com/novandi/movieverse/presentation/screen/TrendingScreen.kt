package com.novandi.movieverse.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novandi.movieverse.R
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.presentation.ui.component.MovieCard
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White

@Composable
fun TrendingScreen() {
    val movies = listOf(
        "https://i.pinimg.com/originals/66/bd/9b/66bd9b1f8354a9aa6900dee35bc2a911.jpg",
        "https://japanesemusicid.com/wp-content/uploads/2018/11/I-Want-to-Eat-Your-Pancreas-Encore-Films.jpg",
        "https://static.wikia.nocookie.net/disasterfilm/images/5/51/2012_poster.jpg"
    )

    Column {
        Text(
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp, top = 32.dp, bottom = 8.dp),
            text = "Trending \uD83D\uDD25",
            color = White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    emojiSupportMatch = EmojiSupportMatch.Default
                )
            )
        )
        Text(
            modifier = Modifier.padding(start = 18.dp, end = 18.dp),
            text = stringResource(id = R.string.trending_desc),
            color = Gray,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        LazyColumn(
            state = rememberLazyListState(),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(movies.size) {
                MovieCard(movie = Movie(1, "", "", "", "", 1.1, "", ""), fullWidth = true)
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun TrendingPreview() {
    MovieVerseTheme {
        TrendingScreen()
    }
}