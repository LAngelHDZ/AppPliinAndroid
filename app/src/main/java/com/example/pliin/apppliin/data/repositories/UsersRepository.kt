package com.example.pliin.apppliin.data.repositories

import com.example.pliin.apppliin.data.database.dao.UserDao
import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.database.entities.UserEntity
import com.example.pliin.apppliin.data.model.providers.UserProvider
import com.example.pliin.apppliin.data.network.services.UserService
import com.example.pliin.apppliin.domain.model.GuideItem
import com.example.pliin.apppliin.domain.model.UserItem
import com.example.pliin.apppliin.domain.model.toDomain
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val api: UserService,
    private val UserProvider: UserProvider,
    private val userDao:UserDao
    ){

    /*
    suspend fun  getAllUser():List<UserModel>{

        val response = api.getUsers()
        UserProvider.users = response
        return response

    }*/

    //Opbtiene las guias de la API
    /*suspend fun  getAllGuideFromApi():List<GuideItem>{
         val response = api.getGuides()
         return response.map {it.toDomain()}
     }*/

    //Opbtiene los usuarios de la API
    suspend fun SetUserItem(user: String, password: String): List<UserItem> {
        return listOf(UserItem(user, password))
    }
    //Opbtiene los usuarios de la API
    /* suspend fun  getAllUserFromApi():List<UserItem>{
         val response = api.getUsers()
         return response.map { it.toDomain() }
     }*/

    //Inserta en la DB los usuarios recuperados de la API,
    suspend fun insertGuides(guides: List<GuideEntity>) {
        userDao.insertAllGuides(guides = guides)
    }

    //Consulta si esxiste un usuaroen la DB y returnoa un Boolean
    suspend fun getLoginUserDatabaseB(user: String, password: String): Boolean {
        val response = userDao.getUserLoginB(user = user, password = password)
        return response
    }

    //Consulta un usuario de la DB y devuelve su informaion
    suspend fun  getLoginUserDatabase(user:String,password:String):List<UserItem>{
        val response: List<UserEntity> = userDao.getUserLogin(user = user, password = password)
        return response.map { it.toDomain() }
    }
    //Consulta todos los usuarios de l BD y devuelve una lista de usuarios
    suspend fun  getAllUserDatabase():List<UserItem>{
        val response: List<UserEntity> = userDao.getUser()
        return response.map { it.toDomain() }
    }

    //Inserta en la DB los usuarios recuperados de la API,
    suspend fun  insertUsers(users:List<UserEntity>){
        userDao.insertAllUsers(users = users)
    }

    //Elimina la informacion de la DB
    suspend fun clearUser(){
        userDao.deleteAllUser()
    }

    //Elimina la informacion de la DB
    suspend fun clearGuide(){
        userDao.deleteAllGuide()
    }

}