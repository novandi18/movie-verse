package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("avatar")
    val avatar: UserAvatar,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String
)

data class UserAvatar(
    @SerializedName("gravatar")
    val gravatar: UserGravatar,

    @SerializedName("tmdb")
    val tmdb: UserTmdb
)

data class UserGravatar(
    @SerializedName("hash")
    val hash: String? = null
)

data class UserTmdb(
    @SerializedName("avatar_path")
    val avatarPath: String? = null
)