package com.novandi.core.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Consts {
    const val DATABASE_NAME = "Movie.db"
    const val MOVIE_TABLE_NAME = "movie"
    const val MOVIE_TRENDING_TABLE_NAME = "movie_trending"
    const val MOVIE_POPULAR_TABLE_NAME = "movie_popular"
    const val USER_TABLE_NAME = "user"
    const val REMOTE_KEY_TABLE_NAME = "remote_key"
    const val SEARCH_TABLE_NAME = "search"
    const val DATA_PREFERENCES = "data_preferences"
    val WELCOME_KEY = booleanPreferencesKey("welcome_key")
    val SESSION_KEY = stringPreferencesKey("session_key")
    val ACCOUNT_KEY = stringPreferencesKey("account_key")
}