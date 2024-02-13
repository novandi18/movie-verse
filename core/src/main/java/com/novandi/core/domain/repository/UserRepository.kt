package com.novandi.core.domain.repository

import com.novandi.core.data.response.Resource
import com.novandi.core.data.source.remote.response.LoginRequest
import com.novandi.core.data.source.remote.response.LogoutRequest
import com.novandi.core.domain.model.AuthResult
import com.novandi.core.domain.model.RequestToken
import com.novandi.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getRequestToken(): Flow<Resource<RequestToken>>
    fun validateAccount(request: LoginRequest): Flow<Resource<AuthResult>>
    fun login(request: LoginRequest): Flow<Resource<AuthResult>>
    fun logout(request: LogoutRequest): Flow<Resource<AuthResult>>
    fun getUser(sessionId: String): Flow<Resource<User>>
    fun getUserFromDB(): Flow<User>
    fun insertUser(user: User)
    fun deleteUser()
}