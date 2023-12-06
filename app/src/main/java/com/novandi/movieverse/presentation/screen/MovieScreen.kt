package com.novandi.movieverse.presentation.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Comment
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.novandi.movieverse.R
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.presentation.ui.component.MovieCarousel
import com.novandi.movieverse.presentation.ui.component.MovieRating
import com.novandi.movieverse.presentation.ui.component.MovieSection
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    movieId: Int,
    navigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var topBarVisibility by rememberSaveable { mutableStateOf(false) }

    if (scrollState.isScrollInProgress) {
        topBarVisibility = scrollState.value >= 400
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier.height(250.dp)
            ) {
                MovieCarousel(
                    itemsCount = 5,
                    itemContent = { index ->
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(
                                            Brush.verticalGradient(
                                                .5f to Black.copy(0f),
                                                1.4f to Black.copy(.8f)
                                            )
                                        )
                                    }
                                },
                            model = "",
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.image_placeholder),
                            error = painterResource(id = R.drawable.image_error),
                            contentScale = ContentScale.Crop
                        )
                    }
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Avengers: End Game",
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Adventure",
                        color = White,
                        fontSize = 16.sp
                    )
                    MovieRating(rating = 8.2)
                }
            }

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
                        contentDescription = null,
                        tint = White
                    )
                    Text(
                        text = "6 December 2023",
                        color = White,
                        fontSize = 14.sp
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
                        contentDescription = null,
                        tint = White
                    )
                    Text(
                        text = "7210",
                        color = White,
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .background(Gray.copy(.2f), RoundedCornerShape(8.dp))
                        .clickable {  }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Comment,
                        contentDescription = null,
                        tint = White
                    )
                    Text(
                        text = "2.8k",
                        color = White,
                        fontSize = 14.sp
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Overview",
                    color = Gray
                )
                Text(
                    text = "While working underground to fix a water main, Brooklyn plumbers—and brothers—Mario and Luigi are transported down a mysterious pipe and wander into a magical new world. But when the brothers are separated, Mario embarks on an epic quest to find Luigi.",
                    color = White
                )
            }

            Divider(
                color = Gray.copy(.2f)
            )

            Divider(
                color = Gray.copy(.2f)
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Tagline",
                    color = Gray
                )
                Text(
                    text = "Not all heroes wear capes. Some wear overalls.",
                    color = White
                )
            }

            Divider(
                color = Gray.copy(.2f)
            )

            Divider(
                color = Gray.copy(.2f)
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            MovieSection(
                sectionName = "Recommended for you",
                movies = Resource.Loading(),
                navigateToMovie = {}
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            MovieSection(
                sectionName = "Similar Movies",
                movies = Resource.Loading(),
                navigateToMovie = {}
            )
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
        }

        TopAppBar(
            title = {
                Text(text = "")
            },
            navigationIcon = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = if (topBarVisibility) Black else Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviePreview() {
    MovieVerseTheme {
        MovieScreen(1, {})
    }
}