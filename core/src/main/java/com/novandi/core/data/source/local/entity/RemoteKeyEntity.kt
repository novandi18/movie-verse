package com.novandi.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.novandi.core.utils.Consts

@Entity(tableName = Consts.REMOTE_KEY_TABLE_NAME)
data class RemoteKeyEntity(
    @PrimaryKey val id: String,
    val currentPage: Int,
    val nextKey: Int?
)