package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.FieldDataModel
import com.example.pliin.apppliin.domain.model.GuideItem
import com.google.gson.annotations.SerializedName

data class FieldDataItem(
    @SerializedName("direcciones::CODS")
    val direccionesCODS: String?,
    @SerializedName("direcciones::dircompleta")
    val direccionesDircompleta: String?,
    @SerializedName("direcciones::nombre")
    val direccionesNombre: String?,
    @SerializedName("direcciones::telefono")
    val direccionesTelefono: String?,
    @SerializedName("IdGuia")
    val idGuia: String?,
    @SerializedName("IdPreM")
    val idPreM: String?,
    @SerializedName("manifiestoPaquetes::estatus")
    val manifiestoPaquetesEstatus: String?,
    @SerializedName("Observacion")
    val observacion: String?,
    @SerializedName("Pesokg")
    val pesokg: Double?,
    @SerializedName("PreManifiestos::Empresa")
    val preManifiestosEmpresa: String?,
    @SerializedName("PreManifiestos::Ruta")
    val preManifiestosRuta: String?,
    @SerializedName("valor_guia")
    val valorGuia: Double?,
    @SerializedName("IntentosEntregasRE::observacion")
    val statusIntento: String?,
    @SerializedName("loadSytem")
    val loadSytem: Boolean? = false
)

fun FieldDataModel.toDomain() =
    FieldDataItem(
        //  clavePrincipal,
        direccionesCODS,
        direccionesDircompleta,
        direccionesNombre,
        direccionesTelefono,
        idGuia,
        idPreM,
        manifiestoPaquetesEstatus,
        observacion,
        pesokg,
        preManifiestosEmpresa,
        preManifiestosRuta,
        valorGuia,
        statusIntento,
        loadSytem
    )

fun GuideItem.toDomain() =
    FieldDataItem(
        //  clavePrincipal,
        direccionesCODS,
        direccionesDircompleta,
        direccionesNombre,
        direccionesTelefono,
        idGuia,
        idPreM,
        manifiestoPaquetesEstatus,
        observacion,
        pesokg,
        preManifiestosEmpresa,
        preManifiestosRuta,
        valorGuia,
        statusIntento,
        loadSytem
    )