package com.novandi.core.domain.model

data class RequestToken(
    val success: Boolean,
    val expiresAt: String,
    val token: String
)
