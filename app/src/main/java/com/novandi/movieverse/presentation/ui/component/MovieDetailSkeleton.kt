package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novandi.movieverse.R
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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

        RatingSkeleton()

        HorizontalDivider(
            color = Gray.copy(.2f)
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.overview),
                color = Gray
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
                .background(Gray))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
                .background(Gray))
            Box(modifier = Modifier
                .fillMaxWidth(.4f)
                .height(15.dp)
                .background(Gray))
        }

        HorizontalDivider(
            color = Gray.copy(.2f)
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.tagline),
                color = Gray
            )
            Box(modifier = Modifier
                .fillMaxWidth(.8f)
                .height(15.dp)
                .background(Gray))
        }

        HorizontalDivider(
            color = Gray.copy(.2f)
        )
    }
}

@Composable
fun RatingSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.rate_this_movie),
            color = Gray
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(5) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.StarRate,
                    contentDescription = null,
                    tint = Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailSkeletonPreview() {
    MovieVerseTheme {
        MovieDetailSkeleton()
    }
}