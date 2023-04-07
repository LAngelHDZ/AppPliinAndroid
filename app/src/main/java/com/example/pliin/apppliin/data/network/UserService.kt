package com.example.pliin.apppliin.data.network

import com.example.pliin.apppliin.data.model.GuideModel
import com.example.pliin.apppliin.data.model.UserModel
import com.example.pliin.apppliin.data.network.response.clients.DataClient
import com.example.pliin.apppliin.data.network.response.clients.UsersClients
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(private val apidata: UsersClients, private val api: DataClient){

   /* suspend fun getUsers(): List<UserModel>{
        return withContext(Dispatchers.IO){
            val response = api.getAllUsers()
            response.body() ?: emptyList()
        }
    }*/

    suspend fun getGuides(): List<GuideModel>{
        return withContext(Dispatchers.IO){
            val response = api.getDataGuide()
            response.body()?.Guias?: emptyList()
        }
    }

    suspend fun getUsers(): List<UserModel>{
        return withContext(Dispatchers.IO){
            val response = api.getDataUser()
            response.body()?.Users?: emptyList()
        }
    }
}