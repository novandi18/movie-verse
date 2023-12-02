package com.novandi.movieverse.presentation.common

import android.os.Parcelable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class NavigationItem(
    val title: String,
    val icon: @RawValue ImageVector,
    val screen: @RawValue Screen,
    val contentDescription: String
) : Parcelable