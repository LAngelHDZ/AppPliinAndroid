package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity(tableName = "manifest_table")
data class ManifestEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo("aux") val aux: String,
    @ColumnInfo("auxPrivilegios") val auxPrivilegios: String,
    @ColumnInfo("ClavePrincipal") val clavePrincipal: String,
    @ColumnInfo("ConsecutivoFolio") val consecutivoFolio: Int,
    @ColumnInfo("CreadoPor") val creadoPor: String,
    @ColumnInfo("Empresa") val empresa: String,
    @ColumnInfo("Fecha") val fecha: String,
    @ColumnInfo("hora") val hora: String,
    @ColumnInfo("IdEmpleado") val idEmpleado: String,
    @ColumnInfo("NombreOperador") val nombreOperador: String,
    @ColumnInfo("Ruta") val ruta: String,
    @ColumnInfo("statusPreM") val statusPreM: String,
    @ColumnInfo("statusRuta") val statusRuta: String,
    @ColumnInfo("tipoMan") val tipoMan: String,
    @ColumnInfo("Totalpqt") val totalpqt: Int,
    @ColumnInfo("TotaolGuias") val totaolGuias: Int
)
