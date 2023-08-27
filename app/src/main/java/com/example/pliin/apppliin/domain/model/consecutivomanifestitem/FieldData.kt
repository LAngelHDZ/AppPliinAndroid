package com.example.pliin.apppliin.domain.model.consecutivomanifestitem


import com.example.pliin.apppliin.data.database.entities.ManifestEntity
import com.example.pliin.apppliin.data.model.consecutivomanifestmodel.FieldDataConsecutivoM
import com.google.gson.annotations.SerializedName

data class FieldData(
    @SerializedName("ConsecutivoFolio")
    val consecutivoFolio: Int?,
    @SerializedName("Fecha")
    val fecha: String?,
    @SerializedName("ClavePrincipal")
    val clavePrincipal: String?,
    @SerializedName("hora")
    val hora: String?,
    @SerializedName("aux")
    val aux: String?,
    @SerializedName("auxPrivilegios")
    val auxPrivilegios: String?,
    @SerializedName("CreadoPor")
    val creadoPor: String?,
    @SerializedName("Empresa")
    val empresa: String?,
    @SerializedName("IdEmpleado")
    val idEmpleado: String?,
    @SerializedName("NombreOperador")
    val nombreOperador: String?,
    @SerializedName("Ruta")
    val ruta: String?,
    @SerializedName("statusPreM")
    val statusPreM: String?,
    @SerializedName("statusRuta")
    val statusRuta: String?,
    @SerializedName("tipoMan")
    val tipoMan: String?,
    @SerializedName("Totalpqt")
    val totalpqt: Int?,
    @SerializedName("TotaolGuias")
    val totaolGuias: Int?
)

fun FieldDataConsecutivoM.toDomain() =
    FieldData(
        consecutivoFolio,
        fecha,
        clavePrincipal,
        hora,
        aux,
        auxPrivilegios,
        creadoPor,
        empresa,
        idEmpleado,
        nombreOperador,
        ruta,
        statusPreM,
        statusRuta,
        tipoMan,
        totalpqt,
        totaolGuias
    )

fun ManifestEntity.toDomain() =
    FieldData(
        consecutivoFolio,
        fecha,
        clavePrincipal,
        hora,
        aux,
        auxPrivilegios,
        creadoPor,
        empresa,
        idEmpleado,
        nombreOperador,
        ruta,
        statusPreM,
        statusRuta,
        tipoMan,
        totalpqt,
        totaolGuias
    )
