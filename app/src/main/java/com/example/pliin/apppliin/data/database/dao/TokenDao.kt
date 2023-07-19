package com.example.pliin.apppliin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pliin.apppliin.data.database.entities.TokenEntity

@Dao
interface TokenDao {

    //consulta el toquen de la DB
    @Query("SELECT * FROM token_table")
    suspend fun getToken(): TokenEntity

    @Query("SELECT Count(*) FROM token_table")
    suspend fun tokenExists(): Boolean

    //Inserta el token en la DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenEntity)

    @Query("DELETE FROM token_table")
    suspend fun deleteToken()
}

