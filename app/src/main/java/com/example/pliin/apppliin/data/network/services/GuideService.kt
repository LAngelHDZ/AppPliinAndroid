package com.example.pliin.apppliin.data.network.services

import android.annotation.SuppressLint
import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.model.datainfo.DataInfoModel
import com.example.pliin.apppliin.data.model.deliverymodel.GetDataGuideDRModel
import com.example.pliin.apppliin.data.model.deliverymodel.MessageModel
import com.example.pliin.apppliin.data.model.deliverymodel.ResponseModel
import com.example.pliin.apppliin.data.model.message.Message
import com.example.pliin.apppliin.data.model.queryguide.QueryGuidePliinModel
import com.example.pliin.apppliin.data.model.queryguide.Response
import com.example.pliin.apppliin.data.model.responserudmodel.ResponseRUDModel
import com.example.pliin.apppliin.data.model.responserudmodel.ResponseSetModel
import com.example.pliin.apppliin.data.network.dto.addguidemanifest.AddGuideToManifest
import com.example.pliin.apppliin.data.network.dto.addguidemanifest.FieldDataAGM
import com.example.pliin.apppliin.data.network.dto.insertguide.FieldData
import com.example.pliin.apppliin.data.network.dto.insertguide.InsertGuideDto
import com.example.pliin.apppliin.data.network.dto.queryguidescanner.QueryGuideDto
import com.example.pliin.apppliin.data.network.dto.queryguidescanner.query
import com.example.pliin.apppliin.data.network.dto.queyguidereception.Query
import com.example.pliin.apppliin.data.network.dto.queyguidereception.QueryGuidePliinDto
import com.example.pliin.apppliin.data.network.response.clients.DataGuideClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GuideService @Inject constructor(
    private val guideapiclient: DataGuideClient,
    private val daotoken: TokenDao
) {
    @SuppressLint("SuspiciousIndentation")
    suspend fun getGuide(guide: String): GetDataGuideDRModel {
        val query = QueryGuideDto(listOf(query(guide, "Asignado")))
        val bearer = daotoken.getToken()?.token
        return withContext(Dispatchers.IO) {
            val response = guideapiclient.getDataguide("Bearer $bearer", query)
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

    suspend fun guideValidateA(guide: String): GetDataGuideDRModel {
        // val query = QueryGuidePliinDto(listOf(Query(guide)))
        val query = QueryGuideDto(listOf(query(guide, "Asignado")))
        val bearer = daotoken.getToken()?.token
        return withContext(Dispatchers.IO) {
            try {
                val response = guideapiclient.guideArrastreValidate("Bearer $bearer", query)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            GetDataGuideDRModel(
                                response.body()?.response,
                                listOf(MessageModel("401", "Invalid FileMaker Data API token (*)"))
                            )
                        }
                        401 -> {
                            GetDataGuideDRModel(
                                response.body()?.response,
                                listOf(MessageModel("401", "Invalid FileMaker Data API token (*)"))
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
                    ResponseModel(
                        emptyList(),
                        DataInfoModel("", 0, "", 0, "", 0)
                    ),
                    listOf(MessageModel("401", "Invalid FileMaker Data API token (*)"))
                )
            }
        }
    }

    suspend fun queryGuide(guide: String): QueryGuidePliinModel {
        val query = QueryGuidePliinDto(listOf(Query(guide)))
        val bearer = daotoken.getToken()?.token
        return withContext(Dispatchers.IO) {
            try {
                val response = guideapiclient.queryGuide("Bearer $bearer", query)
                val data = if (response.isSuccessful) {
                    response.body()!!
                } else {
                    when (response.code()) {
                        500 -> {
                            QueryGuidePliinModel(
                                listOf(Message("401", "No records match the request")),
                                response.body()?.response
                            )
                        }
                        401 -> {
                            QueryGuidePliinModel(
                                listOf(Message("952", "Invalid FileMaker Data API token (*)")),
                                response.body()?.response,
                            )
                        }
                        else -> {
                            QueryGuidePliinModel(
                                listOf(Message("401", "Invalid FileMaker Data API token (*)")),
                                response.body()?.response
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                QueryGuidePliinModel(
                    listOf(Message("500", "Internet Connection Error")),
                    Response(
                        listOf(),
                        DataInfoModel("", 0, "", 0, "", 0)
                    )
                )
            }
        }
    }

    suspend fun insertyGuide(guide: String): ResponseRUDModel {
        val query = InsertGuideDto(FieldData(guide))
        val bearer = daotoken.getToken()?.token
        return withContext(Dispatchers.IO) {
            try {
                val response = guideapiclient.insertGuide("Bearer $bearer", query)
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
                    listOf(Message("500", "No internet conection"))
                )
            }
        }
    }

    suspend fun addGuideManifest(data: List<String>): ResponseRUDModel {
        val query = AddGuideToManifest(
            FieldDataAGM(
                data.component1(),
                data.component2(),
                data.component3(),
                data.component4()
            )
        )

        val bearer = daotoken.getToken()?.token
        return withContext(Dispatchers.IO) {
            try {
                val response = guideapiclient.addGuideManifest("Bearer $bearer", query)
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
}
