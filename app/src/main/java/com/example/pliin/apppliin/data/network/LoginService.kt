package com.example.pliin.apppliin.data.network

import com.example.pliin.apppliin.data.network.response.clients.LoginClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient) {

    suspend fun dologin(user:String, password:String): Boolean{
        return  withContext(Dispatchers.IO){
            val response =  loginClient.dologin(user,password)
            response.body()?.success?:false
        }
    }
}