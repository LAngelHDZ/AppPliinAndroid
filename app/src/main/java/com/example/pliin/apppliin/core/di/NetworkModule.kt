package com.example.pliin.apppliin.core.di

import com.example.pliin.apppliin.core.di.interceptors.BasicAuthInterceptor
import com.example.pliin.apppliin.core.di.interceptors.BodyInterceptor
import com.example.pliin.apppliin.core.di.interceptors.RetryConectionInterceptor
import com.example.pliin.apppliin.data.network.response.clients.*
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
//import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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

            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val trustManagerFactory: TrustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers: Array<TrustManager> =
                trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                "Unexpected default trust managers:" + trustManagers.contentToString()
            }
            val trustManager =
                trustManagers[0] as X509TrustManager
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val gson = Gson()
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
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
}