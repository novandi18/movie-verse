package com.novandi.movieverse.di

import com.novandi.movieverse.data.repository.MovieRepositoryImpl
import com.novandi.movieverse.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repository: MovieRepositoryImpl): MovieRepository
}