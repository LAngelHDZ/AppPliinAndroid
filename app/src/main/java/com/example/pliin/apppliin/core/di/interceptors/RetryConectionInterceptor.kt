package com.example.pliin.apppliin.core.di.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryConectionInterceptor(private val maxRetries: Int) : Interceptor {
    private var retryCount = 0

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var code = response.code
        while ((!response.isSuccessful || code != 500 || code != 200) && retryCount < maxRetries) {
            retryCount++
            response.close()
            Log.d("Intentando conectar", "$retryCount")
            // Realizar una espera antes de intentar nuevamente (opcional)
            Thread.sleep(2000) // Esperar 2 segundos antes de reintentar

            response = chain.proceed(request)
        }
        return response
    }

}