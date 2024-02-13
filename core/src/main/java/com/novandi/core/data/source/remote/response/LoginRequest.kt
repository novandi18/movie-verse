package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("request_token")
    val requestToken: String
)
