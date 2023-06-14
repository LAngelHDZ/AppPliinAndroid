package com.example.pliin.apppliin.data.network.services

import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.model.message.Message
import com.example.pliin.apppliin.data.model.responseupdatestatus.ResponseUpdate
import com.example.pliin.apppliin.data.model.responseupdatestatus.ResponseUpdateStatusModel
import com.example.pliin.apppliin.data.network.dto.intentoentrega.FieldDataTryD
import com.example.pliin.apppliin.data.network.dto.intentoentrega.TryingDeliveryDto
import com.example.pliin.apppliin.data.network.dto.queryguidescanner.createstatus.CreateStatusGuideDto
import com.example.pliin.apppliin.data.network.dto.queryguidescanner.createstatus.FieldDataCreateS
import com.example.pliin.apppliin.data.network.dto.updatestatus.*
import com.example.pliin.apppliin.data.network.dto.updatestatus.FieldData
import com.example.pliin.apppliin.data.network.response.clients.DataGuideClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class UpdateStatusGuideService @Inject constructor(
    private val apiclient: DataGuideClient,
    private val daotoken: TokenDao

) {

    suspend fun setUpdateStatus(status: String?, recordId: String): ResponseUpdateStatusModel {
        val bearer = daotoken.getToken()?.token
        val query = UpdatStatusDto(
            FieldData(
                status
            )
        )

        return withContext(Dispatchers.IO) {
            try {
                val response = apiclient.updateStatus("Bearer $bearer", recordId, query)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ResponseUpdateStatusModel(
                                listOf(Message("500", "No records match the request")),
                                response.body()?.response
                            )
                        }
                        401 -> {
                            ResponseUpdateStatusModel(
                                listOf(Message("952", "Invalid FileMaker Data API token (*)")),
                                response.body()?.response
                            )
                        }

                        else -> {
                            ResponseUpdateStatusModel(
                                listOf(Message("500", "No records match the request")),
                                response.body()?.response
                            )
                        }
                    }
                }
                data

            } catch (e: IOException) {
                // Error de red, como falta de conexión
                e.printStackTrace()
                // Manejar el error de desconexión
                ResponseUpdateStatusModel(
                    listOf(Message("500", "Error de red")),
                    ResponseUpdate("", "")
                )
            }
        }
    }

    suspend fun setCreateStatus(guide: String, status: String): ResponseUpdateStatusModel {
        val bearer = daotoken.getToken()?.token
        val queryCreate = CreateStatusGuideDto(FieldDataCreateS(guide, status))

        return withContext(Dispatchers.IO) {

            try {
                val response = apiclient.createStatus("Bearer $bearer", queryCreate)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ResponseUpdateStatusModel(
                                listOf(Message("500", "No records match the request")),
                                response.body()?.response
                            )
                        }
                        401 -> {
                            ResponseUpdateStatusModel(
                                listOf(Message("952", "Invalid FileMaker Data API token (*)")),
                                response.body()?.response
                            )
                        }


                        else -> {
                            ResponseUpdateStatusModel(
                                listOf(Message("500", "No records match the request")),
                                response.body()?.response
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                // Error de red, como falta de conexión
                e.printStackTrace()
                // Manejar el error de desconexión
                ResponseUpdateStatusModel(
                    listOf(Message("500", "Error de red")),
                    ResponseUpdate("", "")
                )
            }

        }
    }

    suspend fun setIntentoEntrega(guide: String, status: String): ResponseUpdateStatusModel {
        val bearer = daotoken.getToken()?.token
        val queryCreate = TryingDeliveryDto(FieldDataTryD(guide, status))

        return withContext(Dispatchers.IO) {
            try {
                val response = apiclient.tryDelivery("Bearer $bearer", queryCreate)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ResponseUpdateStatusModel(
                                listOf(Message("500", "No records match the request")),
                                response.body()?.response
                            )
                        }
                        else -> {
                            ResponseUpdateStatusModel(
                                listOf(Message("500", "No records match the request")),
                                response.body()?.response
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                ResponseUpdateStatusModel(
                    listOf(Message("500", "No records match the request")),
                    ResponseUpdate("", "")
                )
            }
        }
    }

}