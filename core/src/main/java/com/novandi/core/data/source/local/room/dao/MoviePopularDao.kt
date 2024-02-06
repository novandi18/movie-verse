package com.novandi.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.novandi.core.data.source.local.entity.MoviePopularEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviePopularDao {
    @Query("SELECT * FROM movie_popular")
    fun getMoviesPopular() : Flow<List<MoviePopularEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviePopular(movie: List<MoviePopularEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMoviePopular(movie: MoviePopularEntity)
}