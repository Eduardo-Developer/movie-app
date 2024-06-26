package com.example.movieapp.di

import com.example.movieapp.data.respository.auth.FirebaseAuthenticationImpl
import com.example.movieapp.data.respository.movie.MovieRepositoryImpl
import com.example.movieapp.domain.repository.auth.FirebaseAuthentication
import com.example.movieapp.domain.repository.movie.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindsFirebaseAuthenticationImpl(
        firebaseAuthenticationImpl: FirebaseAuthenticationImpl
    ): FirebaseAuthentication

    @Binds
    abstract fun bindsMovieRepositoryImpl(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}