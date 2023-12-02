package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.White

@Composable
fun MovieRating(rating: Double) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = if (rating > 0) Color.Yellow else Gray,
            )
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = if (rating >= 3) Color.Yellow else Gray,
            )
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = if (rating > 5) Color.Yellow else Gray,
            )
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = if (rating > 7) Color.Yellow else Gray,
            )
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = if (rating > 9) Color.Yellow else Gray,
            )
        }
        Text(
            text = rating.toString(),
            fontSize = 14.sp,
            color = White
        )
    }
}