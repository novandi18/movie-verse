package com.novandi.core.domain.model

data class AuthResult(
    val success: Boolean,
    val requestToken: String? = null,
    val sessionId: String? = null,
    val statusMessage: String? = null
)
