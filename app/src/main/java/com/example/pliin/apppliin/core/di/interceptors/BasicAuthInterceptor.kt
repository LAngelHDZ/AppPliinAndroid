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
        val url= request.url.toUrl()
        val contenType= request.body?.contentType().toString()
        val headers = request.headers.values("Content-Type")
        Log.i("Conten-tType", contenType.toString())
        Log.i("Headers", headers.toString())
        Log.i("URL impresa", url.toString())


        val contenType2= request.body?.contentType().toString()
        Log.i("Conten-tType2", contenType2.toString())
        val authenticatedRequest: Request = if(contenType == "application/json; charset=UTF-8" || contenType=="null"){
            Log.i("reuest modificado", "modificado")

            if(contenType=="null"){
                request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
            }else request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "/")
               .addHeader("Accept-Encoding", "UTF-8")
                .build()
        }else{
            Log.i("requess original", request.toString())
             request.newBuilder()
                 .addHeader("Content-Type", "multipart/form-data")
             .build()
        }
        return chain.proceed(authenticatedRequest)
    }
}