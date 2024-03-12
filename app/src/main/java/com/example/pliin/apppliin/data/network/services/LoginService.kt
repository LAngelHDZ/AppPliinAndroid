package com.example.pliin.apppliin.data.network.services

import com.example.pliin.apppliin.core.di.interceptors.BasicAuthInterceptor
import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.model.TokenModel
import com.example.pliin.apppliin.data.model.avisopago.AvisoModel
import com.example.pliin.apppliin.data.model.message.Message
import com.example.pliin.apppliin.data.network.dto.AvisoPAgoDTO
import com.example.pliin.apppliin.data.network.dto.QueryAvisoPago

import com.example.pliin.apppliin.data.network.dto.UserDTO
import com.example.pliin.apppliin.data.network.response.clients.LoginClient

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import java.io.IOException
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient, private val daoToken: TokenDao) {

    suspend fun dologin(user: String, password: String): TokenModel {
        var credentials: String = Credentials.basic(user, password)
        return withContext(Dispatchers.IO) {
            val response = loginClient.dologin(credentials)
            response.body()?.response ?: TokenModel("")
        }
    }

    suspend fun avisoPago():AvisoModel?{
        val query = AvisoPAgoDTO(listOf(
            QueryAvisoPago(
                "api"
            )
        ))
        val bearer = daoToken.getToken().token

        return withContext(Dispatchers.IO){
            try {
                val response = loginClient.avisoPAgo("Bearer $bearer", query)
                val res = response.body()?.response
                val mes = response.body()?.messages

                val data = if(response.isSuccessful){
                    response.body()
                }else{
                    when(response.code()){

                        500 -> {
                            AvisoModel(
                                mes,
                                res
                            )
                        }
                        400->{
                            AvisoModel(
                                mes,
                                res
                            )
                        }
                        else->{
                            AvisoModel(
                                mes,
                                res
                            )
                        }
                    }
                }
                data
            }catch (e: IOException){
                AvisoModel(
                    listOf(Message("Error","Error de conexión al servidor")),
                    null
                )
            }
        }
    }
}