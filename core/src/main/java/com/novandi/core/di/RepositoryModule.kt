package com.novandi.core.di

import com.novandi.core.data.repository.MovieRepositoryImpl
import com.novandi.core.data.repository.SearchRepositoryImpl
import com.novandi.core.data.repository.UserRepositoryImpl
import com.novandi.core.domain.repository.MovieRepository
import com.novandi.core.domain.repository.SearchRepository
import com.novandi.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun provideUserRepository(repository: UserRepositoryImpl): UserRepository
}