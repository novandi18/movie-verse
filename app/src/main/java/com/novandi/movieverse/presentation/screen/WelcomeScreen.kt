package com.novandi.movieverse.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.common.Welcome
import com.novandi.movieverse.presentation.ui.component.WelcomePager
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.ui.theme.rubikFamily

@Composable
fun WelcomeScreen(
    navigateToHome: () -> Unit
) {
    val listComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = R.raw.welcome_list)
    )
    val searchComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = R.raw.welcome_search)
    )
    val favoriteComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = R.raw.welcome_favorite)
    )
    val slides = listOf(
        Welcome(
            listComposition,
            stringResource(id = R.string.welcome_list_title),
            stringResource(id = R.string.welcome_list_desc)
        ),
        Welcome(
            searchComposition,
            stringResource(id = R.string.welcome_search_title),
            stringResource(id = R.string.welcome_search_desc)
        ),
        Welcome(
            favoriteComposition,
            stringResource(id = R.string.welcome_favorite_title),
            stringResource(id = R.string.welcome_favorite_desc)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        contentAlignment = Alignment.Center
    ) {
        WelcomePager(
            itemsCount = slides.size,
            navigateToHome = navigateToHome,
            itemContent = { index ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                ) {
                    LottieAnimation(
                        modifier = Modifier.size(280.dp),
                        composition = slides[index].image,
                        iterations = LottieConstants.IterateForever
                    )

                    Text(
                        text = slides[index].title,
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = rubikFamily,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )

                    Text(
                        text = slides[index].description,
                        color = White,
                        fontSize = 16.sp,
                        fontFamily = rubikFamily,
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomePreview() {
    MovieVerseTheme {
        WelcomeScreen({})
    }
}