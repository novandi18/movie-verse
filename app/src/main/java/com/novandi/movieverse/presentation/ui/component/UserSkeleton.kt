package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.SemiBlack

@Composable
fun UserSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 60.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(SemiBlack))
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.width(300.dp).height(32.dp).background(SemiBlack))
            Spacer(modifier = Modifier.width(150.dp).height(20.dp).background(SemiBlack))
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(70.dp).background(SemiBlack))
            VerticalDivider(
                modifier = Modifier
                    .height(32.dp)
                    .width(1.dp),
                color = SemiBlack
            )
            Spacer(modifier = Modifier.size(70.dp).background(SemiBlack))
            VerticalDivider(
                modifier = Modifier
                    .height(32.dp)
                    .width(1.dp),
                color = SemiBlack
            )
            Spacer(modifier = Modifier.size(70.dp).background(SemiBlack))
        }
    }
}

@Preview
@Composable
private fun UserSkeletonPreview() {
    MovieVerseTheme {
        Box(modifier = Modifier.fillMaxSize().background(Black)) {
            UserSkeleton()
        }
    }
}