package com.novandi.core.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novandi.core.data.source.local.entity.MovieTrendingEntity

@Dao
interface MovieTrendingDao {
    @Query("SELECT * FROM movie_trending")
    fun getMovies(): PagingSource<Int, MovieTrendingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<MovieTrendingEntity>)

    @Query("DELETE FROM movie_trending")
    suspend fun clearAll()
}