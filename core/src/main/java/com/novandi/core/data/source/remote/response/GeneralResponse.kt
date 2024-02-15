package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("status_message")
    val statusMessage: String
)
