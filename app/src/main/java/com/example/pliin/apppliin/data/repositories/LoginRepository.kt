package com.example.pliin.apppliin.data.repositories

import android.util.Log
import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.database.entities.TokenEntity
import com.example.pliin.apppliin.data.database.entities.UserEntity
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.data.model.TokenModel
import com.example.pliin.apppliin.data.network.services.LoginService
import com.example.pliin.apppliin.data.network.services.LogoutService
import com.example.pliin.apppliin.domain.model.TokenItem
import com.example.pliin.apppliin.domain.model.UserItem
import com.example.pliin.apppliin.domain.model.responselogoutitem.ResponseLogoutItem
import com.example.pliin.apppliin.domain.model.responselogoutitem.toDomain
import com.example.pliin.apppliin.domain.model.toDomain
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val logaout: LogoutService,
    private val login: LoginService,
    private val tokenDao: TokenDao
) {

    suspend fun dologin(user: String, password: String): TokenItem {
        val response = login.dologin(user, password)
        // tokenDao.insertToken(response.toDatabase())
        Log.i("System token", "${response.token}")
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
    suspend fun getTokenDB(): TokenItem {
        val response: TokenEntity = tokenDao.getToken()
        return response.toDomain()
    }

    //Elimina el token  de la DB
    suspend fun clearToken() {
        tokenDao.deleteToken()
    }

}