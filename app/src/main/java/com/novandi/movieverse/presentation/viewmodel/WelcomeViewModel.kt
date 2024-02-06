package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novandi.core.data.store.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    fun setIsWelcomed() {
        viewModelScope.launch {
            dataStoreManager.setIsWelcome(true)
        }
    }
}