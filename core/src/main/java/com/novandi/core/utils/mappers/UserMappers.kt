package com.novandi.core.utils.mappers

import com.novandi.core.data.source.local.entity.UserEntity
import com.novandi.core.data.source.remote.response.AuthResponse
import com.novandi.core.data.source.remote.response.RequestTokenResponse
import com.novandi.core.data.source.remote.response.UserResponse
import com.novandi.core.domain.model.AuthResult
import com.novandi.core.domain.model.RequestToken
import com.novandi.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object UserMappers {
    fun requestTokenResponseToDomain(input: RequestTokenResponse): Flow<RequestToken> = flowOf(
        RequestToken(input.success, input.expiresAt, input.token)
    )

    fun authResponseToDomain(input: AuthResponse): Flow<AuthResult> = flowOf(
        AuthResult(
            success = input.success,
            requestToken = input.requestToken,
            sessionId = input.sessionId,
            statusMessage = input.statusMessage
        )
    )

    fun userEntityToDomain(input: UserEntity?): User =
        User(
            id = input?.id ?: 0,
            name = input?.name ?: "",
            username = input?.username ?: "",
            gravatar = input?.gravatar?.ifEmpty { null },
            tmdb = input?.tmdb?.ifEmpty { null }
        )

    fun userDomainToEntity(input: User): UserEntity =
        UserEntity(
            id = input.id,
            name = input.name,
            username = input.username,
            gravatar = input.gravatar ?: "",
            tmdb = input.tmdb ?: "",
            favoriteMovies = input.favoriteMovies ?: 0,
            ratedMovies = input.ratedMovies ?: 0,
            watchlistMovies = input.watchlistMovies ?: 0
        )

    fun userResponseToDomain(input: UserResponse): Flow<User> = flowOf(
        User(
            id = input.id,
            name = input.name,
            username = input.username,
            gravatar = input.avatar.gravatar.hash ?: "",
            tmdb = input.avatar.tmdb.avatarPath ?: ""
        )
    )
}