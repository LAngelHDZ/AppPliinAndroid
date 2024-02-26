package com.example.pliin.apppliin.core.di

import com.example.pliin.apppliin.core.di.interceptors.BasicAuthInterceptor
import com.example.pliin.apppliin.core.di.interceptors.BodyInterceptor
import com.example.pliin.apppliin.data.network.response.clients.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    //private val BaseURL = "https://10.0.2.2:7067"
    private val BaseURL = "https://sidigq.ddns.net/"

    @Singleton
    @Provides
    fun provideRetrofit(
    ): Retrofit {
        val gson: Gson = GsonBuilder()
            //.setLenient()
            // .enableComplexMapKeySerialization()
            .serializeNulls()
            // .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            // .setVersion(1.0)
            .create()
        return Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getUnsafeOkHttpClient()!!)
            .build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val gson = Gson()
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(BasicAuthInterceptor())
            builder.connectTimeout(5, TimeUnit.SECONDS)
            builder.readTimeout(3, TimeUnit.SECONDS)
            builder.writeTimeout(3, TimeUnit.SECONDS)
            builder.addInterceptor(BodyInterceptor())
            // builder.addInterceptor(RetryConectionInterceptor(3))
            builder.addInterceptor(loggingInterceptor)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
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
    fun provideDataGuideClient(retrofit: Retrofit): DataGuideClient {
        return retrofit.create(DataGuideClient::class.java)
    }

    @Singleton
    @Provides
    fun provideDataEmployeeClient(retrofit: Retrofit): DataEmployeeClient {
        return retrofit.create(DataEmployeeClient::class.java)
    }

    @Singleton
    @Provides
    fun provideManifest(retrofit: Retrofit): DataManifestClient {
        return retrofit.create(DataManifestClient::class.java)
    }
}