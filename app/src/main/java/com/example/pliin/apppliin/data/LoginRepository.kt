package com.example.pliin.apppliin.data

import com.example.pliin.apppliin.data.network.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: LoginService){

    suspend fun dologin(user:String, password:String):Boolean{
        return api.dologin(user, password)
    }
}