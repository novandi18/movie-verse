package com.novandi.movieverse.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novandi.movieverse.data.source.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RemoteKeyEntity)

    @Query("SELECT * FROM remote_key WHERE id = :id")
    suspend fun getById(id: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_key")
    suspend fun deleteAll()
}