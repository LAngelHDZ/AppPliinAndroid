package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pliin.apppliin.domain.model.GuideItem
import com.google.gson.annotations.SerializedName

@Entity(tableName = "guide_table")
data class GuideEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int=0,
    @ColumnInfo("cods") val direccionesCODS: String?,
    @ColumnInfo("direccion") val direccionesDircompleta: String?,
    @ColumnInfo("cliente") val direccionesNombre: String?,
    @ColumnInfo("telefono") val direccionesTelefono: String?,
    @ColumnInfo("idGuia") val idGuia: String?,
    @ColumnInfo("idPreM") val idPreM: String?,
    @ColumnInfo("estatus") val manifiestoPaquetesEstatus: String?,
    @ColumnInfo("observacion") val observacion: String?,
    @ColumnInfo("pesokg") val pesokg: Double?,
    @ColumnInfo("empresa") val preManifiestosEmpresa: String?,
    @ColumnInfo("ruta") val preManifiestosRuta: String?,
    @ColumnInfo("valorGuia") val valorGuia: Double?,
    @ColumnInfo("observacionIntento") val statusIntento: String?,
)

fun GuideItem.toDatabase() = GuideEntity(
    direccionesCODS=direccionesCODS,
    direccionesDircompleta=direccionesDircompleta,
    direccionesNombre=direccionesNombre,
    direccionesTelefono=direccionesTelefono,
    idGuia=idGuia,
    idPreM= idPreM,
    manifiestoPaquetesEstatus=manifiestoPaquetesEstatus,
    observacion=observacion,
    pesokg=pesokg,
    preManifiestosEmpresa=preManifiestosEmpresa,
    preManifiestosRuta=preManifiestosRuta,
    valorGuia=valorGuia,
    statusIntento= statusIntento
)