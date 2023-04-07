package com.example.pliin.apppliin.core.di

import com.example.pliin.apppliin.data.network.response.clients.DataClient
import com.example.pliin.apppliin.data.network.response.clients.LoginClient
import com.example.pliin.apppliin.data.network.response.clients.UsersClients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val BaseURL = "https://run.mocky.io/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit): LoginClient {
        return retrofit.create(LoginClient::class.java)
    }

    @Singleton
    @Provides
    fun provideUSerClient(retrofit: Retrofit): UsersClients {
        return retrofit.create(UsersClients::class.java)
    }

    @Singleton
    @Provides
    fun provideDataClient(retrofit: Retrofit): DataClient {
        return retrofit.create(DataClient::class.java)
    }
}