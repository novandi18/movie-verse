package com.novandi.movieverse.presentation.common

import com.airbnb.lottie.LottieComposition

data class Welcome(
    val image: LottieComposition?,
    val title: String,
    val description: String
)