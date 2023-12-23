package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme

@Composable
fun MovieDetailSkeleton() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Gray)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                        .background(Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp)
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
            Box(modifier = Modifier.fillMaxWidth().height(15.dp).background(Gray))
            Box(modifier = Modifier.fillMaxWidth().height(15.dp).background(Gray))
            Box(modifier = Modifier.fillMaxWidth(.4f).height(15.dp).background(Gray))
        }

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
            Box(modifier = Modifier.fillMaxWidth(.8f).height(15.dp).background(Gray))
        }

        Divider(
            color = Gray.copy(.2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailSkeletonPreview() {
    MovieVerseTheme {
        MovieDetailSkeleton()
    }
}