package com.example.pliin.apppliin.data.network.response.clients

import com.example.pliin.apppliin.data.model.consecutivomanifestmodel.ConsecutivoManModel
import com.example.pliin.apppliin.data.model.deliverymodel.GetDataGuideDRModel
import com.example.pliin.apppliin.data.model.responserudmodel.ResponseRUDModel
import com.example.pliin.apppliin.data.network.dto.createmanifest.CreateManifestDto
import com.example.pliin.apppliin.data.network.dto.getconsecutivomanifiesto.GetConsecutivoManifestDto
import com.example.pliin.apppliin.data.network.dto.getguidemanifest.GetGuidesManifestDto
import com.example.pliin.apppliin.data.network.dto.getmanifest.GetManifestDto
import com.example.pliin.apppliin.data.network.dto.reasignarguide.ReasignarGuideDto
import com.example.pliin.apppliin.data.network.dto.updatemanifest.UpdateManifestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface DataManifestClient {

    //EndPoint para consulta una guia en un manifiesto
    @POST("/fmi/data/v2/databases/PLIIN/layouts/ConsecutivoPreMan/_find/")
    suspend fun getConsecutive(
        @Header("Authorization") bearer: String,
        @Body query: GetConsecutivoManifestDto,
    ): Response<ConsecutivoManModel>

    @POST("/fmi/data/v2/databases/PLIIN/layouts/PreManifiesto/_find/")
    suspend fun getManifest(
        @Header("Authorization") bearer: String,
        @Body query: GetManifestDto,
    ): Response<ConsecutivoManModel>

    @POST("/fmi/data/v2/databases/PLIIN/layouts/PreManifiesto/records/")
    suspend fun createManifest(
        @Header("Authorization") bearer: String,
        @Body query: CreateManifestDto,
    ): Response<ResponseRUDModel>

    @PATCH("/fmi/data/v2/databases/PLIIN/layouts/PreManifiesto/records/{recordId}/")
    suspend fun updateManifest(
        @Header("Authorization") bearer: String,
        @Path("recordId") recordId: String,
        @Body query: UpdateManifestDto,
    ): Response<ResponseRUDModel>

    @PATCH("/fmi/data/v2/databases/PLIIN/layouts/GuiasAPI/records/{recordId}/")
    suspend fun reasignarGuide(
        @Header("Authorization") bearer: String,
        @Path("recordId") recordId: String,
        @Body query: ReasignarGuideDto,
    ): Response<ResponseRUDModel>

    //EndPoint para consulta una guia en un manifiesto
    @POST("/fmi/data/v2/databases/PLIIN/layouts/GuiasAPI/_find/")
    suspend fun getGuidesManifest(
        @Header("Authorization") bearer: String,
        @Body query: GetGuidesManifestDto,
    ): Response<GetDataGuideDRModel>

}