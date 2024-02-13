package com.novandi.core.di

import com.novandi.core.domain.usecase.MovieInteractor
import com.novandi.core.domain.usecase.MovieUseCase
import com.novandi.core.domain.usecase.SearchInteractor
import com.novandi.core.domain.usecase.SearchUseCase
import com.novandi.core.domain.usecase.UserInteractor
import com.novandi.core.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor) : MovieUseCase

    @Binds
    @Singleton
    abstract fun provideSearchUseCase(searchInteractor: SearchInteractor) : SearchUseCase

    @Binds
    @Singleton
    abstract fun provideUserUseCase(userInteractor: UserInteractor) : UserUseCase
}