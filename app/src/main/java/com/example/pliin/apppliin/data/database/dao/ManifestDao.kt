package com.example.pliin.apppliin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.database.entities.ManifestEntity

@Dao
interface ManifestDao{
    //Verifica si existe una session activa
    @Query("SELECT * FROM manifest_table WHERE ClavePrincipal = :folioManifest")
    suspend fun getManifest(folioManifest:String): ManifestEntity?

    @Query("SELECT * FROM manifest_table")
    suspend fun getAllManifest(): List<ManifestEntity?>

    @Query("SELECT * FROM manifest_table WHERE statusPreM = :status")
    suspend fun getAllManifestAplicado(status:String ="APLICADO"): List<ManifestEntity?>

    @Query("UPDATE manifest_table SET statusPreM = :status WHERE clavePrincipal = :claveManifest")
    suspend fun updateStatusManifest(claveManifest:String,status:String)

    //Inserta la informaciòn del empleado
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManifest(manifest: ManifestEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllManifest(manifest: List<ManifestEntity?> )
}