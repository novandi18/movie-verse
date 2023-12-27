package com.novandi.movieverse.domain.model

data class MovieReview(
    val id: Int,
    val page: Int,
    val totalPages: Int,
    val results: List<MoviewReviewItem>,
    val totalResults: Int
)

data class MoviewReviewItem(
    val authorDetails: MoviewReviewAuthor,
    val createdAt: String,
    val content: String
)

data class MoviewReviewAuthor(
    val avatarPath: String? = null,
    val name: String,
    val username: String
)