package com.example.assessment.providers

import com.example.assessment.repository.SearchUseCase
import com.example.assessment.repository.SearchUseCaseImplementation
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
}
