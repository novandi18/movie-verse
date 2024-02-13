package com.novandi.movieverse.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.novandi.core.data.response.Resource
import com.novandi.core.data.source.remote.response.LoginRequest
import com.novandi.core.data.source.remote.response.LogoutRequest
import com.novandi.core.data.store.DataStoreManager
import com.novandi.core.domain.model.AuthResult
import com.novandi.core.domain.model.RequestToken
import com.novandi.core.domain.model.User
import com.novandi.core.domain.usecase.MovieUseCase
import com.novandi.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val userUseCase: UserUseCase,
    private val movieUseCase: MovieUseCase
): ViewModel() {
    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordVisible by mutableStateOf(false)
        private set

    var loading by mutableStateOf(false)
        private set

    val sessionId = dataStoreManager.sessionId.asLiveData()
    
    val userFromDB = userUseCase.getUserFromDB().asLiveData()

    private val _requestToken = MutableStateFlow<Resource<RequestToken>?>(null)
    val requestToken: StateFlow<Resource<RequestToken>?> get() = _requestToken

    private val _validateAccount = MutableStateFlow<Resource<AuthResult>?>(null)
    val validateAccount: StateFlow<Resource<AuthResult>?> get() = _validateAccount

    private val _authResult = MutableStateFlow<Resource<AuthResult>?>(null)
    val authResult: StateFlow<Resource<AuthResult>?> get() = _authResult

    private val _user = MutableStateFlow<Resource<User>?>(null)
    val user: StateFlow<Resource<User>?> get() = _user

    private val _favoriteMovies = MutableStateFlow<Resource<Int>?>(null)
    val favoriteMovies: StateFlow<Resource<Int>?> get() = _favoriteMovies

    private val _ratedMovies = MutableStateFlow<Resource<Int>?>(null)
    val ratedMovies: StateFlow<Resource<Int>?> get() = _ratedMovies

    private val _watchlistMovies = MutableStateFlow<Resource<Int>?>(null)
    val watchlistMovies: StateFlow<Resource<Int>?> get() = _watchlistMovies

    fun onUsernameChange(value: String) {
        username = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun onPasswordVisibleChange(isVisible: Boolean) {
        passwordVisible = isVisible
    }

    fun onLoadingChange(isLoading: Boolean) {
        loading = isLoading
    }

    fun setIsLoggedIn(isLoggedIn: Boolean, sessionId: String) {
        viewModelScope.launch {
            dataStoreManager.setSessionId(isLoggedIn, sessionId)
        }
    }

    fun requestToken() {
        viewModelScope.launch {
            userUseCase.getRequestToken()
                .catch { err ->
                    _requestToken.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _requestToken.value = result
                }
        }
    }

    fun validateAccount(request: LoginRequest) {
        viewModelScope.launch {
            userUseCase.validateAccount(request)
                .catch { err ->
                    _validateAccount.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _validateAccount.value = result
                }
        }
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            userUseCase.login(request)
                .catch { err ->
                    _authResult.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _authResult.value = result
                }
        }
    }

    fun getUser(sessionId: String) {
        viewModelScope.launch {
            userUseCase.getUser(sessionId)
                .catch { err ->
                    _user.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _user.value = result
                }
        }
    }

    fun logout(request: LogoutRequest) {
        viewModelScope.launch {
            userUseCase.logout(request)
        }
    }

    fun insertUser(user: User) = userUseCase.insertUser(user)

    fun deleteUser() = userUseCase.deleteUser()

    fun getFavoriteMoviesTotal(accountId: Int) {
        viewModelScope.launch {
            movieUseCase.getFavoriteMoviesTotal(accountId)
                .catch { err ->
                    _favoriteMovies.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _favoriteMovies.value = result
                }
        }
    }

    fun getRatedMoviesTotal(accountId: Int) {
        viewModelScope.launch {
            movieUseCase.getRatedMoviesTotal(accountId)
                .catch { err ->
                    _ratedMovies.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _ratedMovies.value = result
                }
        }
    }

    fun getWatchlistMoviesTotal(accountId: Int) {
        viewModelScope.launch {
            movieUseCase.getWatchlistMoviesTotal(accountId)
                .catch { err ->
                    _watchlistMovies.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _watchlistMovies.value = result
                }
        }
    }
}