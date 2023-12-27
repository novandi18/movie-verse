package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme

@Composable
fun ReviewSheetItemSkeleton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .size(40.dp)
                .background(Gray, CircleShape)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .height(12.dp)
                    .background(Gray)
            )
            Spacer(
                modifier = Modifier.fillMaxWidth().height(12.dp).background(Gray)
            )
            Spacer(
                modifier = Modifier.fillMaxWidth(.6f).height(12.dp).background(Gray)
            )
        }
    }
}

@Preview
@Composable
private fun ReviewSheetItemSkeletonPreview() {
    MovieVerseTheme {
        ReviewSheetItemSkeleton()
    }
}