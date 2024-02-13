package com.novandi.core.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status_message")
    val message: String,
    val success: Boolean? = false
)