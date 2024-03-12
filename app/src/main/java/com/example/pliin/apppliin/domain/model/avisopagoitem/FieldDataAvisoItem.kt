package com.example.pliin.apppliin.domain.model.avisopagoitem


import com.example.pliin.apppliin.data.model.avisopago.FieldDataAvisoModel
import com.google.gson.annotations.SerializedName

data class FieldDataAvisoItem(
    @SerializedName("access")
    val access: String?, // api
    @SerializedName("aviso_show")
    val avisoShow: Int?, // 0
    @SerializedName("bloqueo_aviso")
    val bloqueoAviso: Int?, // 0
    @SerializedName("ClavePrincipal")
    val clavePrincipal: String?, // B58F9982-B8D0-8947-8D48-0F7E08EC6F00
    @SerializedName("contador")
    val contador: Int?, // 0
    @SerializedName("enabled_contador")
    val enabledContador: Int?, // 0
    @SerializedName("fecha_factura")
    val fechaFactura: String?, // 04/13/2024
    @SerializedName("numero_pago_quincena")
    val numeroPagoQuincena: Int?, // 1
    @SerializedName("stattus_pago")
    val stattusPago: Int? // false
)

fun FieldDataAvisoModel.toDomain() = FieldDataAvisoItem(
    access, avisoShow, bloqueoAviso, clavePrincipal, contador, enabledContador, fechaFactura, numeroPagoQuincena, stattusPago
)