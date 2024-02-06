package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.novandi.core.data.store.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dataStoreManager: DataStoreManager
): ViewModel() {
    val isWelcomed = dataStoreManager.isWelcomed.asLiveData()
}