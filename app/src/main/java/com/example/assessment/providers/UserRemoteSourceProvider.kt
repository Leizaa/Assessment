package com.example.assessment.providers

import com.example.assessment.repository.UserRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRemoteSourceProvider {
    @Singleton
    @Provides
    fun provideUserRemoteSource(retrofit: Retrofit): UserRemoteSource {
        return retrofit.create(UserRemoteSource::class.java)
    }
}
