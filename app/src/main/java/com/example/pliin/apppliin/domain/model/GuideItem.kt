package com.example.pliin.apppliin.domain.model

import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.model.GuideModel
import com.example.pliin.apppliin.domain.model.deliveryItem.FieldDataItem
import com.google.gson.annotations.SerializedName


data class GuideItem(
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
    val statusIntento: String?
)

fun GuideModel.toDomain() = GuideItem(
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

fun FieldDataItem.toDomain() = GuideItem(
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


fun GuideEntity.toDomain() = GuideItem(
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