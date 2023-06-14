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
import java.io.IOException
import javax.inject.Inject

class DeliveryService @Inject constructor(
    private val apiclient: DataGuideClient,
    private val daotoken: TokenDao

) {
    suspend fun setDelivery(
        guide: String,
        recibe: String?,
        parent: String?
    ): ResponseRegisterDeliveryModel {
        val bearer = daotoken.getToken()?.token
        val query = RegisterDeliveryDto(FieldData(guide, parent, recibe))

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
}