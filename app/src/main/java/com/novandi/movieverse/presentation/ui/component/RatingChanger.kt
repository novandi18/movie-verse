package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme

@Composable
fun RatingChanger(
    onRateChange: (Int) -> Unit,
    rate: Int = 0
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(5) { index ->
            IconButton(
                onClick = { onRateChange((index + 1) * 2) }
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = if ((index + 1) <= (rate / 2)) Icons.Default.StarRate
                    else Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = if ((index + 1) <= (rate / 2)) Color.Yellow else Gray
                )
            }
        }
    }
}

@Preview
@Composable
private fun RatingChangerPreview() {
    MovieVerseTheme {
        RatingChanger(onRateChange = {}, rate = 6)
    }
}