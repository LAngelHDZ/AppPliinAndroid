package com.example.pliin.apppliin.core.di.interceptors


import android.util.Log
import com.example.pliin.apppliin.data.network.dto.UserDTO
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class BasicAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val content= request.url.toUrl()
        Log.i("URL impresa", content.toString())
        val authenticatedRequest: Request = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "/")
            .addHeader("Accept-Encoding", "UTF-8")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}