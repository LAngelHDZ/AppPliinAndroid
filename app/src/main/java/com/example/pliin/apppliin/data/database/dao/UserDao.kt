package com.example.pliin.apppliin.data.database.dao

import androidx.room.*
import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.database.entities.UserEntity
import com.example.pliin.apppliin.data.database.entities.relations.UserWithManifest

@Dao
interface UserDao {
    @Query("SELECT COUNT(*) FROM user_table WHERE username = :user AND password = :password")
    suspend fun getUserLoginB(user:String, password:String):Boolean

    @Query("SELECT * FROM user_table WHERE username = :user AND password = :password")
    suspend fun getUserLogin(user:String, password:String):List<UserEntity>

    @Query("SELECT * FROM user_table")
    suspend fun getUser():List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGuides(guides: List<GuideEntity>)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

    @Query("DELETE FROM guide_table")
    suspend fun deleteAllGuide()

    @Transaction
    @Query("SELECT * FROM user_table")
    fun getUsersWithManifest():List<UserWithManifest>
}