package com.novandi.core.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.novandi.core.utils.Consts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val isWelcomed: Flow<Boolean?> = dataStore.data.map { preferences ->
        preferences[Consts.WELCOME_KEY] ?: false
    }

    suspend fun setIsWelcome(isWelcome: Boolean) {
        dataStore.edit { preferences ->
            preferences[Consts.WELCOME_KEY] = isWelcome
        }
    }
}