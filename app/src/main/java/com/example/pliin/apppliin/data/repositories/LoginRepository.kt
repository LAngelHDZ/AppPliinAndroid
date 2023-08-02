package com.example.pliin.apppliin.data.repositories

import android.util.Log
import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.database.dao.UserDao
import com.example.pliin.apppliin.data.database.entities.TokenEntity
import com.example.pliin.apppliin.data.database.entities.UserEntity
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.data.model.TokenModel
import com.example.pliin.apppliin.data.network.services.LoginService
import com.example.pliin.apppliin.data.network.services.LogoutService
import com.example.pliin.apppliin.domain.model.SessionItem
import com.example.pliin.apppliin.domain.model.TokenItem
import com.example.pliin.apppliin.domain.model.UserItem
import com.example.pliin.apppliin.domain.model.responselogoutitem.ResponseLogoutItem
import com.example.pliin.apppliin.domain.model.responselogoutitem.toDomain
import com.example.pliin.apppliin.domain.model.toDomain
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val logaout: LogoutService,
    private val login: LoginService,
    private val tokenDao: TokenDao,
    private val userDao: UserDao
){

    //Crea una session de usuario
    suspend fun CreateSession(user: String,password: String,token:String){
        val session = SessionItem(user,password,token)
        val response = userDao.createSession(session.toDatabase())
    }
    //Consigue los datos de la session
    suspend fun getSession():SessionItem{
        val response = userDao.getUserSession()
        return response.toDomain()
    }

    //Verifica si existe una sesion activa de un usuarios offline e Online
    suspend fun onSession():Boolean {
       /* val response: = tokenDao.getToken()
        var token: String = ""
        if (response?.token.isNullOrBlank()) {
            token = ""
        } else {
            token = response?.token!!
        }*/
        return userDao.onSession()
    }

    //Elimina la session actual
    suspend fun closeSession() {
        userDao.deleteSession()
    }




    suspend fun dologinDB(user: String, password: String): TokenItem {
        val response = login.dologin(user, password)
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }
    suspend fun dologin(user: String, password: String): TokenItem {
        val response = login.dologin(user, password)
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    suspend fun dologout(): ResponseLogoutItem {
        val response = logaout.dologout()
        return response.toDomain()
    }

    //Inserta en la DB el token de acceso
    suspend fun insertToken(token: TokenEntity) {
        tokenDao.insertToken(token)
    }

    //Consulta el token de la DB
    suspend fun getTokenDB(): TokenItem? {
        val response: TokenEntity = tokenDao.getToken()
        return response.toDomain()
    }

    //Consulta si el token existe
    suspend fun tokenDBExists():Boolean {
        return  tokenDao.tokenExists()
    }



    suspend fun getToken(): String? {
        val response: TokenEntity? = tokenDao.getToken()
        var token: String = ""
        if (response?.token.isNullOrBlank()) {
            token = ""
        } else {
            token = response?.token!!
        }
        return token
    }

    //Elimina el token  de la DB
    suspend fun clearToken() {
        tokenDao.deleteToken()
    }

}