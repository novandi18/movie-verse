package com.novandi.movieverse.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.ui.component.LoadStateError
import com.novandi.movieverse.presentation.ui.component.MovieCard
import com.novandi.movieverse.presentation.ui.component.MovieCardSkeleton
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.SemiBlack
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToMovie: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocus = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BackHandler {
        if (viewModel.isSearching) {
            viewModel.onSearchingChange(false)
            focusRequester.requestFocus()
        } else {
            navigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusChanged { focus ->
                                if (focus.isFocused) {
                                    viewModel.onSearchingChange(false)
                                } else {
                                    viewModel.onSearchingChange(true)
                                }
                            },
                        value = viewModel.searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.search_movies),
                                color = Gray
                            )
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = White,
                            fontSize = 16.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Black,
                            unfocusedContainerColor = Black,
                            focusedIndicatorColor = Black,
                            unfocusedIndicatorColor = Black,
                            cursorColor = White
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (viewModel.searchQuery.isNotEmpty()) {
                                    localFocus.clearFocus()
                                    keyboardController?.hide()
                                    viewModel.onSearchingChange(true)
                                    viewModel.saveSearch()
                                }
                            }
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            focusRequester.requestFocus()
                            viewModel.onSearchingChange(false)
                            viewModel.onSearchQueryChange("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(id = R.string.search),
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Black,
                    navigationIconContentColor = White,
                    scrolledContainerColor = SemiBlack
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (viewModel.isSearching) {
                val movies = viewModel.searchMovie().collectAsLazyPagingItems()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(movies.itemCount) { index ->
                        MovieCard(movie = movies[index]!!, navigateToMovie = navigateToMovie)
                    }

                    movies.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    repeat(5) {
                                        MovieCardSkeleton(fullWidth = true)
                                    }
                                }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val error = movies.loadState.refresh as LoadState.Error
                                item {
                                    LoadStateError(errorMessage = error.error.message!!) {
                                        retry()
                                    }
                                }
                            }

                            loadState.append is LoadState.Loading -> {
                                item {
                                    repeat(5) {
                                        MovieCardSkeleton(fullWidth = true)
                                    }
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                val error = movies.loadState.append as LoadState.Error
                                item {
                                    LoadStateError(errorMessage = error.error.localizedMessage!!) {
                                        retry()
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val searchHistories = viewModel.searchHistories.observeAsState(listOf())
                val searchHistoriesByQuery = searchHistories.value.filter { search ->
                    search.keyword.contains(viewModel.searchQuery)
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchHistoriesByQuery.size) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(0.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Black,
                                contentColor = White
                            ),
                            onClick = {
                                localFocus.clearFocus()
                                keyboardController?.hide()
                                viewModel.onSearchQueryChange(searchHistories.value[it].keyword)
                                viewModel.onSearchingChange(true)
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = searchHistoriesByQuery[it].keyword,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 16.sp
                                )
                                IconButton(onClick = { viewModel.deleteSearch(searchHistoriesByQuery[it].id) }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    MovieVerseTheme {
        SearchScreen(navigateToMovie = {}, navigateBack = {})
    }
}