package com.example.assessment.providers

import com.example.assessment.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseProvider {
    @Singleton
    @Provides
    fun provideSearchUserListUseCase(
        useCase: SearchUseCaseImplementation
    ): SearchUseCase = useCase

    @Singleton
    @Provides
    fun provideGetUserDetailUseCase(
        useCase: GetUserDetailUseCaseImplementation
    ): GetUserDetailUseCase = useCase

    @Singleton
    @Provides
    fun provideGetRepoUseCase(
        useCase: GetRepoUseCaseImplementation
    ): GetRepoUseCase = useCase
}
