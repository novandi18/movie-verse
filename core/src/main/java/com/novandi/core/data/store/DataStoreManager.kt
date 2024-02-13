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

    val sessionId: Flow<String?> = dataStore.data.map { preferences ->
        preferences[Consts.SESSION_KEY] ?: ""
    }

    suspend fun setIsWelcome(isWelcome: Boolean) {
        dataStore.edit { preferences ->
            preferences[Consts.WELCOME_KEY] = isWelcome
        }
    }

    suspend fun setSessionId(isLogin: Boolean, sessionId: String) {
        dataStore.edit { preferences ->
            preferences[Consts.SESSION_KEY] = if (isLogin) sessionId else ""
        }
    }
}