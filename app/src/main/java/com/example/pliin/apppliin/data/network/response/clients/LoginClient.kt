package com.example.pliin.apppliin.data.network.response.clients

import com.example.pliin.apppliin.data.network.response.LoginRensponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginClient {
    @FormUrlEncoded
    @POST("/v3/ecb753f1-f4e0-4b79-882e-165eeb91c86a")
    suspend fun dologin(
        @Field("user") user: String,
        @Field("password") password: String,
    ): Response<LoginRensponse>
}