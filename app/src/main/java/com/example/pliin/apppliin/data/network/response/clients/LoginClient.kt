package com.example.pliin.apppliin.data.network.response.clients

import com.example.pliin.apppliin.data.network.response.loginresponse.LoginRensponse
import com.example.pliin.apppliin.data.network.response.tokenapiresponse.ResponseLogoutModel
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface LoginClient {
  /*  @Headers(
        "Content-Type:application/json"

     )*/

    @POST("/fmi/data/v1/databases/PLIIN/sessions")


    suspend fun dologin(
        @Header("Authorization") auth: String,
//        @Header("Content-Type") content:String="application/json"
    ): Response<LoginRensponse>

    @DELETE("/fmi/data/v1/databases/PLIIN/sessions/{token}")
    suspend fun dologout(
        @Path("token") token: String
    ): Response<ResponseLogoutModel>
}