package com.novandi.movieverse.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.novandi.movieverse.R
import com.novandi.movieverse.domain.model.MoviewReviewAuthor
import com.novandi.movieverse.domain.model.MoviewReviewItem
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.utils.formatDateTime
import com.novandi.movieverse.utils.splitAtIndex
import com.novandi.movieverse.utils.toImageUrl

@Composable
fun ReviewSheetItem(
    data: MoviewReviewItem?
) {
    val maxChars = 75
    var expandComment by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            model = data?.authorDetails?.avatarPath.toImageUrl(),
            placeholder = painterResource(id = R.drawable.image_placeholder),
            error = painterResource(id = R.drawable.image_error)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(.65f)
                ) {
                    Text(
                        text = if (data?.authorDetails?.name != "") data!!.authorDetails.name else stringResource(id = R.string.no_name),
                        fontSize = 16.sp,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "@${data.authorDetails.username}",
                        fontSize = 12.sp,
                        color = Gray
                    )
                }
                Text(
                    text = formatDateTime(data?.createdAt.toString()),
                    fontSize = 12.sp,
                    color = Gray
                )
            }
            if (data!!.content.length > maxChars) {
                data.content.splitAtIndex(maxChars).also { (short, remainder) ->
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = White)) {
                                append(if (expandComment) short + remainder else "$short... ")
                            }

                            withStyle(style = SpanStyle(color = Gray)) {
                                append(stringResource(
                                    id = if (expandComment) R.string.show_less else R.string.show_more
                                ))
                            }
                        },
                        modifier = Modifier.clickable { expandComment = !expandComment },
                        fontSize = 16.sp
                    )
                }
            } else {
                Text(
                    text = data.content,
                    fontSize = 16.sp,
                    color = White
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReviewSheetItemPreview() {
    MovieVerseTheme {
        ReviewSheetItem(
            data = MoviewReviewItem(
                authorDetails = MoviewReviewAuthor(
                    name = "Novandi Ramadhan",
                    avatarPath = "",
                    username = "novandi18"
                ),
                createdAt = "2023-07-27T15:34:43.183Z",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            )
        )
    }
}