package com.novandi.movieverse.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.novandi.core.data.response.Resource
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.ui.component.MovieSection
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.SemiBlack
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.ui.theme.rubikFamily
import com.novandi.movieverse.presentation.viewmodel.ExploreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    navigateToMovie: (Int) -> Unit,
    navigateToSearch: () -> Unit
) {
    val popularMovies by viewModel.popularMovies.observeAsState(initial = Resource.Loading())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 18.dp, top = 32.dp),
            text = stringResource(id = R.string.explore),
            color = White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = rubikFamily
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SemiBlack,
                contentColor = White
            ),
            onClick = navigateToSearch
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.search_movies),
                    fontSize = 16.sp,
                    fontFamily = rubikFamily
                )
                Icon(imageVector = Icons.Rounded.Search, contentDescription = stringResource(id = R.string.search))
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        MovieSection(sectionName = stringResource(id = R.string.popular), popularMovies, navigateToMovie = navigateToMovie)
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ExplorePreview() {
    MovieVerseTheme {
        ExploreScreen(navigateToMovie = {}, navigateToSearch = {})
    }
}