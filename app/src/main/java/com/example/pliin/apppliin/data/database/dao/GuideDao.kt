package com.example.pliin.apppliin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pliin.apppliin.data.database.entities.EmployeeEntity
import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.database.entities.TokenEntity
import com.example.pliin.apppliin.domain.model.GuideItem

@Dao
interface GuideDao {

    //Inserta Registro de guia en la DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuide(guide: GuideEntity)


    //Comsulta la informacion de las guias del manifiesto
    @Query("SELECT * FROM guide_table")
    suspend fun getGuidesDB():List<GuideEntity>

    @Query("DELETE FROM guide_table")
    suspend fun deleteAllGuide()
}