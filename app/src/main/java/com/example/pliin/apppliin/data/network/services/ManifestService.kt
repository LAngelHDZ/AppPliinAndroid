package com.example.pliin.apppliin.data.network.services

import android.annotation.SuppressLint
import android.util.Log
import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.model.consecutivomanifestmodel.ConsecutivoManModel
import com.example.pliin.apppliin.data.model.consecutivomanifestmodel.ResponseConsecutive
import com.example.pliin.apppliin.data.model.datainfo.DataInfoModel
import com.example.pliin.apppliin.data.model.deliverymodel.GetDataGuideDRModel
import com.example.pliin.apppliin.data.model.deliverymodel.MessageModel
import com.example.pliin.apppliin.data.model.message.Message
import com.example.pliin.apppliin.data.model.responserudmodel.ResponseSetModel
import com.example.pliin.apppliin.data.model.responserudmodel.ResponseRUDModel
import com.example.pliin.apppliin.data.network.dto.createmanifest.CreateManifestDto
import com.example.pliin.apppliin.data.network.dto.createmanifest.FieldData
import com.example.pliin.apppliin.data.network.dto.getconsecutivomanifiesto.GetConsecutivoManifestDto
import com.example.pliin.apppliin.data.network.dto.getconsecutivomanifiesto.Query
import com.example.pliin.apppliin.data.network.dto.getconsecutivomanifiesto.Sort
import com.example.pliin.apppliin.data.network.dto.getguidemanifest.GetGuidesManifestDto
import com.example.pliin.apppliin.data.network.dto.getguidemanifest.QueryGGM
import com.example.pliin.apppliin.data.network.dto.getmanifest.GetManifestDto
import com.example.pliin.apppliin.data.network.dto.getmanifest.Querym
import com.example.pliin.apppliin.data.network.dto.getmanifest.Sortm
import com.example.pliin.apppliin.data.network.dto.reasignarguide.FieldDataRG
import com.example.pliin.apppliin.data.network.dto.reasignarguide.ReasignarGuideDto
import com.example.pliin.apppliin.data.network.dto.updatemanifest.FieldDataUM
import com.example.pliin.apppliin.data.network.dto.updatemanifest.UpdateManifestDto
import com.example.pliin.apppliin.data.network.response.clients.DataManifestClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ManifestService @Inject constructor(
    private val dataManifestClient: DataManifestClient,
    private val daoToken: TokenDao
) {
    suspend fun consecutivoMan(date: String): ConsecutivoManModel {
        val bearer = daoToken.getToken()?.token!!

        val query = GetConsecutivoManifestDto(
            listOf(Query("<=$date")),
            listOf(Sort("Fecha"), Sort("hora"))
        )
        return withContext(Dispatchers.IO) {

            try {
                val response = dataManifestClient.getConsecutive("Bearer $bearer", query)
                val data = if (response.isSuccessful) {
                    Log.d("En retrofit objeto M", "${response.body()}")
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ConsecutivoManModel(
                                response.body()?.response,
                                listOf(Message("500", "No found Record"))
                            )
                        }
                        400 -> {
                            ConsecutivoManModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                        else -> {
                            ConsecutivoManModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                ConsecutivoManModel(
                    ResponseConsecutive(
                        DataInfoModel("", 0, "", 0, "", 0),
                        emptyList()
                    ),
                    listOf(Message("500", "No found Record"))
                )
            }
        }
    }

    suspend fun getManifest(data: List<String>): ConsecutivoManModel{
        val bearer = daoToken.getToken().token
        val query = GetManifestDto(
            listOf(Querym(data.component1(), data.component2(),data.component4())),
            listOf(Sortm("Fecha"), Sortm("hora")),
            data.component3()
        )
//        val query = GetConsecutivoManifestDto(
//            listOf(Query("<=$date")),
//            listOf(Sort("Fecha"), Sort("hora"))
//        )
        return withContext(Dispatchers.IO){
            try {
                val response = dataManifestClient.getManifest("Bearer $bearer", query)
                val data = if (response.isSuccessful) {
                    Log.d("En retrofit objeto M", "${response.body()}")
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ConsecutivoManModel(
                                response.body()?.response,
                                listOf(Message("500", "No found Record"))
                            )
                        }

                        400 -> {
                            ConsecutivoManModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }

                        else -> {
                            ConsecutivoManModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                    }
                }
                data
            } catch (e: IOException){
                ConsecutivoManModel(
                    ResponseConsecutive(
                        DataInfoModel("", 0, "", 0, "", 0),
                        emptyList()
                    ),
                    listOf(Message("500", "No found Record"))
                )
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun createManifest(data: List<String>): ResponseRUDModel {
        val bearer = daoToken.getToken().token!!

        val query = CreateManifestDto(
            FieldData(
                data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                data[5],
                data[6],
                data[7],
                data[8]
            )
        )
        return withContext(Dispatchers.IO){
            try {
                val response = dataManifestClient.createManifest("Bearer $bearer", query)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("500", "No found Record"))
                            )
                        }
                        400 -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                        else -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                ResponseRUDModel(
                    ResponseSetModel("", ""),
                    listOf(Message("500", "No found Record"))
                )
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun updateManifest(data: List<String>): ResponseRUDModel {
        val bearer = daoToken.getToken().token!!

        val query = UpdateManifestDto(
            FieldDataUM(
                data[0],
                data[1],
                data[2],
                data[4]
            )
        )
        return withContext(Dispatchers.IO) {
            try {
                val response = dataManifestClient.updateManifest("Bearer $bearer",data[3], query)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("500", "No found Record"))
                            )
                        }
                        400 -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                        else -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                ResponseRUDModel(
                    ResponseSetModel("", ""),
                    listOf(Message("500", "No found Record"))
                )
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun reasignarGuideManifest(idRecord:String): ResponseRUDModel {
        val bearer = daoToken.getToken()?.token!!

        val query = ReasignarGuideDto(
            FieldDataRG("Reasignado")
        )

        return withContext(Dispatchers.IO) {
            try {
                val response = dataManifestClient.reasignarGuide("Bearer $bearer",idRecord,query)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("500", "No found Record"))
                            )
                        }
                        400 -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                        else -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("400", "No found Record"))
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                ResponseRUDModel(
                    ResponseSetModel("", ""),
                    listOf(Message("500", "No found Record"))
                )
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getGuidesManifest(claveManifest: String): GetDataGuideDRModel {
        val query = GetGuidesManifestDto(listOf(QueryGGM(claveManifest,"Asignado")))
        val bearer = daoToken.getToken()?.token
        return withContext(Dispatchers.IO) {
            val response = dataManifestClient.getGuidesManifest("Bearer $bearer", query)
            try {
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            GetDataGuideDRModel(
                                response.body()?.response,
                                listOf(MessageModel("401", "No records match the request"))
                            )
                        }

                        401 -> {
                            GetDataGuideDRModel(
                                response.body()?.response,
                                listOf(MessageModel("952", "Invalid FileMaker Data API token (*)"))
                            )
                        }

                        else -> {
                            GetDataGuideDRModel(
                                response.body()?.response,
                                listOf(MessageModel("401", "Invalid FileMaker Data API token (*)"))
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                GetDataGuideDRModel(
                    response.body()?.response,
                    listOf(MessageModel("500", "Error no network"))
                )

            }
        }
    }

}