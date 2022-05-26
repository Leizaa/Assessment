package com.example.assessment.providers

import com.example.assessment.repository.UserRepository
import com.example.assessment.repository.UserRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {
    @Singleton
    @Provides
    fun provideUserRepository(
        repository: UserRepositoryImplementation
    ): UserRepository = repository
}
