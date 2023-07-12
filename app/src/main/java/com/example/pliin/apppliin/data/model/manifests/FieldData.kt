package com.example.pliin.apppliin.data.model.manifests


import com.google.gson.annotations.SerializedName

data class FieldData(
    @SerializedName("aux")
    val aux: String,
    @SerializedName("auxPrivilegios")
    val auxPrivilegios: String,
    @SerializedName("ClavePrincipal")
    val clavePrincipal: String,
    @SerializedName("ConsecutivoFolio")
    val consecutivoFolio: Int,
    @SerializedName("CreadoPor")
    val creadoPor: String,
    @SerializedName("Empresa")
    val empresa: String,
    @SerializedName("Fecha")
    val fecha: String,
    @SerializedName("hora")
    val hora: String,
    @SerializedName("IdEmpleado")
    val idEmpleado: String,
    @SerializedName("NombreOperador")
    val nombreOperador: String,
    @SerializedName("Ruta")
    val ruta: String,
    @SerializedName("statusPreM")
    val statusPreM: String,
    @SerializedName("statusRuta")
    val statusRuta: String,
    @SerializedName("tipoMan")
    val tipoMan: String,
    @SerializedName("Totalpqt")
    val totalpqt: Int,
    @SerializedName("TotaolGuias")
    val totaolGuias: Int
)