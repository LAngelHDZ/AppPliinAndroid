package com.example.pliin.apppliin.data.network.services

import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.network.response.clients.LoginClient
import com.example.pliin.apppliin.data.network.response.tokenapiresponse.MessageLogout
import com.example.pliin.apppliin.data.network.response.tokenapiresponse.ResponseLogout
import com.example.pliin.apppliin.data.network.response.tokenapiresponse.ResponseLogoutModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutService @Inject constructor(
    private val loginClient: LoginClient,
    private val dao: TokenDao
) {
    suspend fun dologout(): ResponseLogoutModel {
        val token = dao.getToken()
        return withContext(Dispatchers.IO) {
            val respopnse = loginClient.dologout(token.token!!)
            respopnse.body() ?: ResponseLogoutModel(
                listOf(
                    MessageLogout(
                        "952",
                        "Invalid FileMaker Data API token (*)"
                    )

                ), ResponseLogout("")
            )
        }
    }
}