package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("expires_at")
    val expiresAt: String,

    @SerializedName("request_token")
    val token: String
)
