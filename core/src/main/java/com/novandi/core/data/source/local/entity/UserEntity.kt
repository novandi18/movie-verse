package com.novandi.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.novandi.core.utils.Consts

@Entity(tableName = Consts.USER_TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("gravatar")
    val gravatar: String,

    @ColumnInfo("tmdb")
    val tmdb: String,

    @ColumnInfo("favorite_movies")
    val favoriteMovies: Int,

    @ColumnInfo("rated_movies")
    val ratedMovies: Int,

    @ColumnInfo("watchlist_movies")
    val watchlistMovies: Int
)
