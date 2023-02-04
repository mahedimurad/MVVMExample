package com.sarwar.mvvmexample.di

import android.content.Context
import com.sarwar.mvvmexample.data.db.MasterDatabase
import com.sarwar.mvvmexample.data.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMasterDatabase(@ApplicationContext context: Context): MasterDatabase{
        return MasterDatabase.getInstance(context)
    }

}