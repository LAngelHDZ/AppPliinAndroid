package com.example.pliin.apppliin.data.network.services

import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.model.message.Message
import com.example.pliin.apppliin.data.model.responseregisterdelivery.Response
import com.example.pliin.apppliin.data.model.responseregisterdelivery.ResponseRegisterDeliveryModel
import com.example.pliin.apppliin.data.network.dto.registerdelivery.FieldData
import com.example.pliin.apppliin.data.network.dto.registerdelivery.RegisterDeliveryDto
import com.example.pliin.apppliin.data.network.response.clients.DataGuideClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

class DeliveryService @Inject constructor(
    private val apiclient: DataGuideClient,
    private val daotoken: TokenDao

) {
    suspend fun setDelivery(
        guide: String,
        recibe: String?,
        parent: String?,
        typePago: String?,
        pago: String
    ): ResponseRegisterDeliveryModel {
        val bearer = daotoken.getToken()?.token
        val query = RegisterDeliveryDto(FieldData(guide, parent, recibe,pago,typePago))

        return withContext(Dispatchers.IO) {
            try {


                val response = apiclient.createDeliveryGuide("Bearer $bearer", query)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ResponseRegisterDeliveryModel(
                                listOf(Message("500", "No records match the request")),
                                response.body()?.response
                            )
                        }
                        else -> {
                            ResponseRegisterDeliveryModel(
                                listOf(Message("500", "No records match the request")),
                                response.body()?.response
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                ResponseRegisterDeliveryModel(
                    listOf(Message("500", "No conection")),
                    Response("", "")
                )

            }
        }
    }

    suspend fun setDeliveryPhoto(
        url: String,
        recordId: String,
    ): ResponseRegisterDeliveryModel {

        val bearer = daotoken.getToken().token

        //Ruta absuoluta de la imagen
        val imagenFile = File(url)

        val requestBody: RequestBody = imagenFile.asRequestBody("application/octet-stream".toMediaType())

        val imagenPart: MultipartBody.Part = MultipartBody.Part.createFormData("upload", imagenFile.name,requestBody)

        return withContext(Dispatchers.IO) {
            try {
                val response = apiclient.setDeliveryGuidePhoto("Bearer $bearer",recordId,imagenPart)

                val data = if (response.isSuccessful) {
                    response.body()
                } else {
                    when (response.code()) {
                        500 -> {
                            ResponseRegisterDeliveryModel(
                                listOf(Message("500", "No records match the request")),
                                Response("", "")
                            )
                        }

                        else -> {
                            ResponseRegisterDeliveryModel(
                                listOf(Message("500", "No records match the request")),
                                Response("", "")
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                ResponseRegisterDeliveryModel(
                    listOf(Message("500", "No conection")),
                    Response("", "")
                )

            } as ResponseRegisterDeliveryModel
        }
    }
}


