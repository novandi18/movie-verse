package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RatingRequest(
    @SerializedName("value")
    val value: Double
)
