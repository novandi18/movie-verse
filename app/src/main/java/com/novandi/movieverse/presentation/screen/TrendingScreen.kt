package com.novandi.movieverse.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.novandi.movieverse.R
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.presentation.ui.component.MovieCard
import com.novandi.movieverse.presentation.ui.component.MovieCardSkeleton
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.viewmodel.TrendingViewModel

@Composable
fun TrendingScreen(
    viewModel: TrendingViewModel = hiltViewModel()
) {
    val trendingMovies by viewModel.trendingMovies.observeAsState(Resource.Loading())
    var data by remember { mutableStateOf<List<Movie>?>(null) }
    var loading by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current

    when (trendingMovies) {
        is Resource.Loading -> loading = true
        is Resource.Success -> {
            loading = false
            data = trendingMovies.data
        }
        is Resource.Error -> {
            loading = false
            data = listOf()
            Toast.makeText(context, trendingMovies.message, Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        item {
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

            if (loading) {
                repeat(5) {
                    MovieCardSkeleton(fullWidth = true)
                }
            }
        }

        if (!loading && data != null) {
            items(data!!.size) { index ->
                MovieCard(data!![index], fullWidth = true)
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
            }
        }

    }
    Spacer(modifier = Modifier.padding(vertical = 16.dp))
}

@Preview(showBackground = true)
@Composable
private fun TrendingPreview() {
    MovieVerseTheme {
        TrendingScreen()
    }
}