package com.example.pliin.apppliin.data.database.dao

import androidx.room.*
import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.database.entities.SessionEntity
import com.example.pliin.apppliin.data.database.entities.UserEntity
import com.example.pliin.apppliin.data.database.entities.relations.UserWithManifest

@Dao
interface UserDao {

    //Valida si existe una sesion activa de un usuario con el usuario y contraseña
    @Query("SELECT COUNT(*) FROM session_table WHERE username = :user AND password = :password")
    suspend fun session(user:String, password:String):Boolean

    //Verifica si existe una session activa
    @Query("SELECT COUNT(*) FROM session_table")
    suspend fun onSession():Boolean

    //Recupera el usaurio con la session iniciada
    @Query("SELECT * FROM session_table")
    suspend fun getUserSession():SessionEntity

    //Inserta los datos de una session del usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createSession(Session: SessionEntity)

    //Elimina la session del usuario
    @Query("DELETE FROM session_table")
    suspend fun deleteSession()

    //Valida si el usuario existe en la Base de datos
    @Query("SELECT COUNT(*) FROM user_table WHERE username = :user AND password = :password")
    suspend fun getUserLoginB(user:String, password:String):Boolean

    @Query("SELECT * FROM user_table WHERE username = :user AND password = :password")
    suspend fun getUserLogin(user:String, password:String):List<UserEntity>

    @Query("SELECT * FROM user_table")
    suspend fun getUser():UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGuides(guides: List<GuideEntity>)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

    @Query("DELETE FROM guide_table")
    suspend fun deleteAllGuide()

//    @Transaction
//    @Query("SELECT * FROM user_table")
//    fun getUsersWithManifest():List<UserWithManifest>
}