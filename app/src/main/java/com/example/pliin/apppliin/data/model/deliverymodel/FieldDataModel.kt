package com.example.pliin.apppliin.data.model.deliverymodel


import com.google.gson.annotations.SerializedName

data class FieldDataModel(
    @SerializedName("IdPreM")
    val idPreM: String?,
    @SerializedName("IdGuia")
    val idGuia: String?,
    @SerializedName("Observacion")
    val observacion: String?,
    @SerializedName("Pesokg")
    val pesokg: Double?,
    @SerializedName("valor_guia")
    val valorGuia: Double?,
    @SerializedName("direcciones::nombre")
    val direccionesNombre: String?,
    @SerializedName("direcciones::telefono")
    val direccionesTelefono: String?,
    @SerializedName("direcciones::dircompleta")
    val direccionesDircompleta: String?,
    @SerializedName("direcciones::CODS")
    val direccionesCODS: String?,
    @SerializedName("PreManifiestos::Ruta")
    val preManifiestosRuta: String?,
    @SerializedName("PreManifiestos::Empresa")
    val preManifiestosEmpresa: String?,
    @SerializedName("manifiestoPaquetes::estatus")
    val manifiestoPaquetesEstatus: String?,
    @SerializedName("IntentosEntregasRE::observacion")
    val statusIntento: String?,
    @SerializedName("loadSytem")
    val loadSytem: Boolean?=false

)