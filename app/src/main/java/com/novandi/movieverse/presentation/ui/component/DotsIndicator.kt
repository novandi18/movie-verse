package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.novandi.movieverse.presentation.ui.theme.Red40
import com.novandi.movieverse.presentation.ui.theme.White

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Red40,
    unSelectedColor: Color = White.copy(.6f),
    dotSize: Dp = 6.dp
) {
    var isNextIndex by remember { mutableStateOf(false) }
    var previousIndex by remember { mutableIntStateOf(-1) }
    isNextIndex = selectedIndex > previousIndex
    previousIndex = selectedIndex

    LazyRow(
        modifier = modifier.wrapContentSize()
    ) {
        items(if (totalDots > 5) 5 else totalDots) { index ->
            IndicatorDot(
                color = if (selectedIndex == 1 && selectedIndex == index) selectedColor
                else if (selectedIndex == (totalDots - 1) && index == 4) selectedColor
                else if (selectedIndex >= 4 && selectedIndex != (totalDots - 1) && index == 3) selectedColor
                else if (selectedIndex < 4 && (index == selectedIndex)) selectedColor
                else unSelectedColor,
                size = dotSize
            )

            if (index != 4) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}