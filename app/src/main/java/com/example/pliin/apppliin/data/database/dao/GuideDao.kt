package com.example.pliin.apppliin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pliin.apppliin.data.database.entities.GuideEntity

@Dao
interface GuideDao {

    //Inserta Registro de guia en la DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuide(guide: GuideEntity)


    //Comsulta la informacion de las guias del manifiesto
    @Query("SELECT * FROM guide_table WHERE idPreM = :folioManifest")
    suspend fun getGuidesDB(folioManifest: String):List<GuideEntity>

    @Query("DELETE FROM guide_table")
    suspend fun deleteAllGuide()
}