package com.sarwar.mvvmexample.di

import com.sarwar.mvvmexample.data.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain: Interceptor.Chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization","Client-ID GEWcXNfJeYOkEhStfk7EyVtCWgwd1Vnj2EozkIbpvfs")
                        chain.proceed(request.build())
                    }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }


}