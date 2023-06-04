package com.example.pliin.apppliin.data.network.services

import com.example.pliin.apppliin.core.di.interceptors.BasicAuthInterceptor
import com.example.pliin.apppliin.data.model.TokenModel

import com.example.pliin.apppliin.data.network.dto.UserDTO
import com.example.pliin.apppliin.data.network.response.clients.LoginClient

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient) {

    suspend fun dologin(user: String, password: String): TokenModel {
        var credentials: String = Credentials.basic(user, password)
        return withContext(Dispatchers.IO) {
            val response = loginClient.dologin(credentials)
            response.body()?.response ?: TokenModel("")
        }
    }
}