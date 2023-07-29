package com.example.pliin.apppliin.core.di.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import okio.BufferedSink
import okio.ByteString.Companion.encodeUtf8
import java.io.IOException

class BodyInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBodyOrigin = originalRequest.body
        val hasEmptyBody = requestBodyOrigin?.let { requestBody ->
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            buffer.readUtf8().isEmpty()
        } ?: true

        val url= originalRequest.url.toUrl()
        val contenType= originalRequest.body?.contentType().toString()

        val requestString = convertRequestBodyToString(requestBodyOrigin)
        val restq = reemplazaCaracter(requestString, '/', ' ')

        // Elimina barras diagonales del RequestBody
        val modifiedRequestBody = converterJson(requestString)
        val modifiedRequestBodyMp = converterMultipart(requestString)

        // Crea una solicitud nueva con el RequestBody modificado
        val newRequest = originalRequest.newBuilder()
            .method(originalRequest.method, modifiedRequestBody)
            .build()

        // Crea una solicitud nueva con el RequestBody modificado
        val newRequestMp = originalRequest.newBuilder()
            .method(originalRequest.method, modifiedRequestBodyMp)
            .build()

        // Envía la solicitud original

        Log.i("Json modificado", convertRequestBodyToString(newRequestMp.body))
        if (hasEmptyBody || contenType == "multipart/form-data") {
            Log.i("request body", "El body es nulo o el contet-type eis multipart/ form data")
        } else {
            Log.i("request body", "El body no es nulo")
        }

        /*return if (hasEmptyBody || contenType == "multipart/form-data") {
            if ( contenType == "multipart/form-data"){
                chain.proceed(originalRequest)
            }else chain.proceed(originalRequest)

        } else {
            if(contenType =="application/json" || !hasEmptyBody){
                chain.proceed(newRequest)
            }else  chain.proceed(originalRequest)
        }*/
        return if (hasEmptyBody){
            chain.proceed(originalRequest)

        }else{
            when(contenType){
                "application/json; charset=UTF-8"->{chain.proceed(newRequest)}
                else -> chain.proceed(originalRequest)
            }
        }

    }


    fun reemplazaCaracter(cadena: String, reemplazado: Char, remplazador: Char): String {
        var arreglo = cadena.toCharArray()
        for (i in arreglo.indices) {
            if (arreglo[i] == reemplazado) {
                arreglo[i] = remplazador
            }
        }
        return arreglo.joinToString(separator = "")
    }

    private fun addQuotesToRequestBodyv(requestBody: RequestBody?): RequestBody? {
        requestBody?.let {
            val buffer = Buffer()
            it.writeTo(buffer)
            val requestBodyString = buffer.readUtf8()

            // Agrega comillas alrededor del JSON del RequestBody
            val modifiedRequestBodyString = "\"$requestBodyString\""

            return object : RequestBody() {
                override fun contentType(): MediaType? {
                    return it.contentType()
                }

                override fun contentLength(): Long {
                    return it.contentLength()
                }

                override fun writeTo(sink: BufferedSink) {
                    sink.writeUtf8(modifiedRequestBodyString)
                }
            }
        }
        return requestBody
    }

    private fun converterJson(bodyrest: String): RequestBody? {
        return bodyrest.encodeUtf8()
            .toRequestBody("application/json".toMediaType())
        // return modifiedRequestBodyString.toRequestBody("application/json".toMediaTypeOrNull())

    }

    private fun converterMultipart(bodyrest: String): RequestBody? {
        return bodyrest.encodeUtf8()
            .toRequestBody("multipart/form-data".toMediaType())
        // return modifiedRequestBodyString.toRequestBody("application/json".toMediaTypeOrNull())

    }

    private fun modifyRequestBody(requestBody: RequestBody?): RequestBody? {
        // Modifica el RequestBody según tus necesidades
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val requestBodyString = buffer.readUtf8()
        val modifiedRequestBodyString = modifyRequestBodyString(requestBodyString)
        return modifiedRequestBodyString.toRequestBody(requestBody?.contentType())
    }

    private fun modifyRequestBodyString(requestBodyString: String): String {
        // Modifica el RequestBodyString según tus necesidades
        val modifiedRequestBodyString = "\"$requestBodyString\""
        return requestBodyString
    }

    private fun addQuotesToRequestBody(requestBody: RequestBody?): RequestBody? {
        val buffer = Buffer()
        requestBody!!.writeTo(buffer)
        val requestBodyString = buffer.readUtf8()
        val modifiedRequestBodyString = "\"$requestBodyString\""
        return modifiedRequestBodyString.toRequestBody(requestBody.contentType())
    }

    private fun convertRequestBodyToString(requestBody: RequestBody?): String {
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        return buffer.readUtf8()
    }

    private fun removeSlashFromRequestBody(requestBody: RequestBody?): RequestBody? {
        val let = requestBody?.let {
            val buffer = Buffer()
            it.writeTo(buffer)
            val requestBodyString = buffer.readUtf8()
            val modifiedRequestBodyString = requestBodyString.replace("/", "")
            return modifiedRequestBodyString.toRequestBody(it.contentType())
        }
        return let
    }
}