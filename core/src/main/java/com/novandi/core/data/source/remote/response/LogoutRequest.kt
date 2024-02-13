package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @SerializedName("session_id")
    val sessionId: String
)
