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
import com.example.pliin.apppliin.data.network.dto.createguidecod.FieldDataCod
import com.example.pliin.apppliin.data.network.dto.createguidecod.RegisterGuideCodDto
import com.example.pliin.apppliin.data.network.dto.datospqt.DatosPqtDto
import com.example.pliin.apppliin.data.network.dto.direccionguide.DireccionGuideDto
import com.example.pliin.apppliin.data.network.dto.direccionguide.FieldDataDG
import com.example.pliin.apppliin.data.network.dto.getGuideCod.GetGuideCodDto
import com.example.pliin.apppliin.data.network.dto.getGuideCod.QueryGGC
import com.example.pliin.apppliin.data.network.dto.insertguide.FieldData
import com.example.pliin.apppliin.data.network.dto.insertguide.InsertGuideDto
import com.example.pliin.apppliin.data.network.dto.queryguidescanner.QueryGuideDto
import com.example.pliin.apppliin.data.network.dto.queryguidescanner.Sort
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
    suspend fun getGuide(guide: String, observation: String, IdPreM: String): GetDataGuideDRModel{
//        val query = QueryGuideDto(listOf(query(guide, "Asignado")))
        val query = QueryGuideDto(listOf(query( IdPreM,guide,observation)),
            listOf(Sort("Fecha"),
                Sort("Hora")
            )
        )

        val bearer = daotoken.getToken().token
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

    @SuppressLint("SuspiciousIndentation")
    suspend fun registerGuideCod(guide: String, valueCod: Float, pago: String?): ResponseRUDModel{
//        val query = QueryGuideDto(listOf(query(guide, "Asignado")))
        val query = RegisterGuideCodDto(FieldDataCod(guide,valueCod))
        val bearer = daotoken.getToken().token
        return withContext(Dispatchers.IO){
            val response = guideapiclient.registerGuideCod("Bearer $bearer", query)
            try{
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

                        401 -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("500", "No found Record"))
                            )
                        }

                        else -> {
                            ResponseRUDModel(
                                response.body()?.response,
                                listOf(Message("500", "No found Record"))
                            )
                        }
                    }
                }
                data
            } catch (e: IOException) {
                ResponseRUDModel(
                    response.body()?.response,
                    listOf(Message("500", "No found Record"))
                )

            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getGuideCod(guide: String): GetDataGuideDRModel{
//        val query = QueryGuideDto(listOf(query(guide, "Asignado")))
        val query = GetGuideCodDto(listOf(QueryGGC(guide)))

        val bearer = daotoken.getToken().token
        return withContext(Dispatchers.IO){
            val response = guideapiclient.getGuideCod("Bearer $bearer", query)
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
        val query = QueryGuideDto(listOf(query( "",guide,"Asignado")),
            listOf(Sort("Fecha"),
                Sort("Hora")
            )
        )
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

    suspend fun queryGuideDireccion(guide: String): QueryGuidePliinModel {
        val query = QueryGuidePliinDto(listOf(Query(guide)))
        val bearer = daotoken.getToken()?.token
        return withContext(Dispatchers.IO) {
            try {
                val response = guideapiclient.queryGuideDireccion("Bearer $bearer", query)
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

    suspend fun queryGuideDatosPqt(guide: String): QueryGuidePliinModel {
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

    suspend fun insertyGuide(guide: String, pago: String?): ResponseRUDModel {
        val query = InsertGuideDto(FieldData(guide,pago))
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

    suspend fun addGuideManifest(data: List<String>, status: String): ResponseRUDModel {
        val query = AddGuideToManifest(
            FieldDataAGM(
                data.component1(),
                data.component2(),
                data.component3(),
                data.component4(),
                status
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

    suspend fun addDireccionGuide(dataDir: List<String>): ResponseRUDModel {
        val query = DireccionGuideDto(
            FieldDataDG(
                dataDir[0],
                dataDir[1],
                dataDir[2],
                dataDir[3],
                dataDir[4],
                dataDir[5],
                dataDir[6],
                dataDir[7]
            )
        )

        val bearer = daotoken.getToken()?.token
        return withContext(Dispatchers.IO) {
            try {
                val response = guideapiclient.addDireccionGuide("Bearer $bearer", query)
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

    suspend fun addDatosPqtGuide(datosPqt: DatosPqtDto): ResponseRUDModel {

        val bearer = daotoken.getToken()?.token
        return withContext(Dispatchers.IO) {
            try {
                val response = guideapiclient.addDatosPqtGuide("Bearer $bearer", datosPqt)
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
