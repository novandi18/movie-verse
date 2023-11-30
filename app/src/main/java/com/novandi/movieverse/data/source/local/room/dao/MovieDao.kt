package com.novandi.movieverse.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.novandi.movieverse.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE movie_type = :movieType")
    fun getUpcomingMovies(movieType: String = "upcoming") : Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE movie_type = :movieType")
    fun getPopularMovies(movieType: String = "popular") : Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)
}