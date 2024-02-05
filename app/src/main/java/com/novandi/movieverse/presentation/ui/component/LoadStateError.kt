package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.ui.theme.rubikFamily

@Composable
fun LoadStateError(errorMessage: String, retry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = errorMessage,
            fontSize = 20.sp,
            color = White,
            fontFamily = rubikFamily
        )
        Button(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            onClick = { retry() },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Gray,
                contentColor = Black
            )
        ) {
            Text(text = stringResource(id = R.string.try_again), color = Black, fontFamily = rubikFamily)
        }
    }
}