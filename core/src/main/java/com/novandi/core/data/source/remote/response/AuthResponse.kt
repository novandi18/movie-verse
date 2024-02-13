package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("expires_at")
    val expiresAt: String? = null,

    @SerializedName("request_token")
    val requestToken: String? = null,

    @SerializedName("session_id")
    val sessionId: String? = null,

    @SerializedName("status_code")
    val statusCode: Int? = null,

    @SerializedName("status_message")
    val statusMessage: String? = null
)
