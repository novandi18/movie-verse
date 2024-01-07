package com.novandi.movieverse.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.novandi.movieverse.utils.Consts

@Entity(tableName = Consts.MOVIE_TRENDING_TABLE_NAME)
data class MovieTrendingEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "poster")
    val poster: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "page")
    var page: Int,
)
