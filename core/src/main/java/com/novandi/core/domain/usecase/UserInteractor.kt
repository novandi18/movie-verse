package com.novandi.core.domain.usecase

import com.novandi.core.data.response.Resource
import com.novandi.core.data.source.remote.response.LoginRequest
import com.novandi.core.data.source.remote.response.LogoutRequest
import com.novandi.core.domain.model.AuthResult
import com.novandi.core.domain.model.RequestToken
import com.novandi.core.domain.model.User
import com.novandi.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) : UserUseCase {
    override fun getRequestToken(): Flow<Resource<RequestToken>> = userRepository.getRequestToken()

    override fun validateAccount(request: LoginRequest): Flow<Resource<AuthResult>> =
        userRepository.validateAccount(request)

    override fun login(request: LoginRequest): Flow<Resource<AuthResult>> =
        userRepository.login(request)

    override fun logout(request: LogoutRequest): Flow<Resource<AuthResult>> =
        userRepository.logout(request)

    override fun getUser(sessionId: String): Flow<Resource<User>> = userRepository.getUser(sessionId)
    override fun getUserFromDB(): Flow<User> = userRepository.getUserFromDB()
    override fun insertUser(user: User) = userRepository.insertUser(user)
    override fun deleteUser() = userRepository.deleteUser()
}