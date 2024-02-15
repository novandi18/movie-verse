package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class FavoriteRequest(
    @SerializedName("media_type")
    val mediaType: String = "movie",

    @SerializedName("media_id")
    val id: Int,

    @SerializedName("favorite")
    val isFavorite: Boolean
)
