package com.example.pliin.apppliin.data.network.response.clients

import com.example.pliin.apppliin.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface UsersClients {
    @GET("/v3/65ae714f-6e03-4f72-8fcf-4efbf347516f")

    suspend fun getAllUsers(): Response<List<UserModel>>

}
