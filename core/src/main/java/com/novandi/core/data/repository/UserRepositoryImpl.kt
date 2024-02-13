package com.novandi.core.data.repository

import com.novandi.core.data.response.NetworkOnlyResource
import com.novandi.core.data.response.Resource
import com.novandi.core.data.source.local.LocalDataSource
import com.novandi.core.data.source.remote.RemoteDataSource
import com.novandi.core.data.source.remote.network.ApiResponse
import com.novandi.core.data.source.remote.response.LoginRequest
import com.novandi.core.data.source.remote.response.AuthResponse
import com.novandi.core.data.source.remote.response.LogoutRequest
import com.novandi.core.data.source.remote.response.RequestTokenResponse
import com.novandi.core.data.source.remote.response.UserResponse
import com.novandi.core.domain.model.AuthResult
import com.novandi.core.domain.model.RequestToken
import com.novandi.core.domain.model.User
import com.novandi.core.domain.repository.UserRepository
import com.novandi.core.utils.AppExecutors
import com.novandi.core.utils.mappers.UserMappers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): UserRepository {
    override fun getRequestToken(): Flow<Resource<RequestToken>> =
        object : NetworkOnlyResource<RequestToken, RequestTokenResponse>() {
            override fun loadFromNetwork(data: RequestTokenResponse): Flow<RequestToken> =
                UserMappers.requestTokenResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<RequestTokenResponse>> =
                remoteDataSource.getRequestToken()
        }.asFlow()

    override fun validateAccount(request: LoginRequest): Flow<Resource<AuthResult>> =
        object : NetworkOnlyResource<AuthResult, AuthResponse>() {
            override fun loadFromNetwork(data: AuthResponse): Flow<AuthResult> =
                UserMappers.authResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<AuthResponse>> =
                remoteDataSource.validateAccount(request)
        }.asFlow()

    override fun login(request: LoginRequest): Flow<Resource<AuthResult>> =
        object : NetworkOnlyResource<AuthResult, AuthResponse>() {
            override fun loadFromNetwork(data: AuthResponse): Flow<AuthResult> =
                UserMappers.authResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<AuthResponse>> =
                remoteDataSource.login(request)
        }.asFlow()

    override fun logout(request: LogoutRequest): Flow<Resource<AuthResult>> =
        object : NetworkOnlyResource<AuthResult, AuthResponse>() {
            override fun loadFromNetwork(data: AuthResponse): Flow<AuthResult> =
                UserMappers.authResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<AuthResponse>> =
                remoteDataSource.logout(request)
        }.asFlow()

    override fun getUser(sessionId: String): Flow<Resource<User>> =
        object : NetworkOnlyResource<User, UserResponse>() {
            override fun loadFromNetwork(data: UserResponse): Flow<User> =
                UserMappers.userResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> =
                remoteDataSource.getUser(sessionId)
        }.asFlow()

    override fun getUserFromDB(): Flow<User> = localDataSource.getUser().map {
        UserMappers.userEntityToDomain(it)
    }

    override fun insertUser(user: User) {
        val entity = UserMappers.userDomainToEntity(user)
        appExecutors.diskIO().execute {
            localDataSource.insertUser(entity)
        }
    }

    override fun deleteUser() {
        appExecutors.diskIO().execute {
            localDataSource.deleteUser()
        }
    }
}