package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class WatchlistRequest(
    @SerializedName("media_type")
    val mediaType: String = "movie",

    @SerializedName("media_id")
    val id: Int,

    @SerializedName("watchlist")
    val isWatchlist: Boolean
)
