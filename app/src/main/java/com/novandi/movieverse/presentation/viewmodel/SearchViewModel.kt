package com.novandi.movieverse.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novandi.core.domain.model.Movie
import com.novandi.core.domain.model.Search
import com.novandi.core.domain.usecase.MovieUseCase
import com.novandi.core.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val searchUseCase: SearchUseCase
): ViewModel() {
    val searchHistories = searchUseCase.getSearch().asLiveData()

    var isSearching by mutableStateOf(false)
        private set

    var searchQuery by mutableStateOf("")
        private set

    fun onSearchingChange(isSearch: Boolean) {
        isSearching = isSearch
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun searchMovie(): Flow<PagingData<Movie>> {
        return movieUseCase.getSearchMovies(searchQuery)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
    }

    fun saveSearch() = searchUseCase.saveSearch(Search(keyword = searchQuery))

    fun deleteSearch(id: Int) = searchUseCase.deleteSearch(id)
}