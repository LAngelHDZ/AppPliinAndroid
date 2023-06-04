package com.example.pliin.apppliin.core.di.interceptors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer

class ConvertBodyToJsonInterceptor(private val gson: Gson) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalRequestBody = request.body
        val hasEmptyBody = originalRequestBody?.let { requestBody ->
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            buffer.readUtf8().isEmpty()
        } ?: true

        if (hasEmptyBody) {
            return chain.proceed(request)
        }

        val requestBodyString = originalRequestBody?.let { requestBody ->
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            buffer.readUtf8()
        }

        val requestBodyType = object : TypeToken<Any>() {}.type
        val requestBodyJson = gson.toJson(requestBodyString, requestBodyType)

        val newRequestBody = requestBodyJson
            .toRequestBody(originalRequestBody?.contentType())

        val newRequest = request.newBuilder()
            .method(request.method, newRequestBody)
            .build()
        return chain.proceed(newRequest)
    }
}