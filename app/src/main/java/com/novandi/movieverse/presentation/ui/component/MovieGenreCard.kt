package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGenreCard(genre: String) {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Gray.copy(.4f),
            contentColor = White
        ),
        onClick = {}
    ) {
        Text(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            text = genre,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieGenreCardPreview() {
    MovieVerseTheme {
        MovieGenreCard("Adventure")
    }
}