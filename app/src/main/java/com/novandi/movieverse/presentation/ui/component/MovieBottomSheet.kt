package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.novandi.movieverse.R
import com.novandi.core.domain.model.MoviewReviewItem
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.ui.theme.rubikFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieBottomSheet(
    showReview: (Boolean) -> Unit,
    sheetState: SheetState,
    reviewData: LazyPagingItems<MoviewReviewItem>
) {
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        onDismissRequest = { showReview(false) },
        sheetState = sheetState,
        containerColor = Black,
        contentColor = White
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 18.dp),
            text = stringResource(id = R.string.reviews, reviewData.itemCount),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = rubikFamily
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(reviewData.itemCount) { index ->
                ReviewSheetItem(
                    data = reviewData[index]
                )
            }

            reviewData.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                repeat(2) {
                                    ReviewSheetItemSkeleton()
                                }
                            }
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = reviewData.loadState.refresh as LoadState.Error
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = error.error.localizedMessage!!,
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
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                repeat(2) {
                                    ReviewSheetItemSkeleton()
                                }
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = reviewData.loadState.refresh as LoadState.Error
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = error.error.localizedMessage!!,
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
                    }
                }
            }
        }
    }
}