package com.example.assessment.providers

import com.example.assessment.BuildConfig
import com.example.assessment.utility.Constants
import com.example.assessment.utility.TrustAllConnectionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {
    @Singleton
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().apply {
            hostnameVerifier { _, _ -> true }
            if (BuildConfig.DEBUG) {
                val httpLogInterceptor = HttpLoggingInterceptor()
                httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLogInterceptor)
            }

            val trustAllCerts = arrayOf<TrustManager>(TrustAllConnectionManager())
            val sslContext = SSLContext.getInstance(Constants.TLS_VERSION)
            sslContext.init(null, trustAllCerts, SecureRandom())

            sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        }
        return okHttpClient
            .connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
}