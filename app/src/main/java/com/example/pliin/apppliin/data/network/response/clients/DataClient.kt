package com.example.pliin.apppliin.data.network.response.clients

import com.example.pliin.apppliin.data.network.response.DataGuideResponse
import com.example.pliin.apppliin.data.network.response.DataUserResponse
import retrofit2.http.GET

interface DataClient {

@GET("/v3/3a91f7aa-4605-417f-bd97-db4f289443d2")

suspend fun getDataUser(): retrofit2.Response<DataUserResponse>

    @GET("/v3/3a91f7aa-4605-417f-bd97-db4f289443d2")
    suspend fun getDataGuide(): retrofit2.Response<DataGuideResponse>
}