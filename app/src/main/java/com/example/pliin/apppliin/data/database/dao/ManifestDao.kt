package com.example.pliin.apppliin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pliin.apppliin.data.database.entities.ManifestEntity

@Dao
interface ManifestDao{
    //Verifica si existe una session activa
    @Query("SELECT * FROM manifest_table")
    suspend fun getManifest(): ManifestEntity?

    //Inserta la informaciòn del empleado
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManifest(manifest: ManifestEntity)
}