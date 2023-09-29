package com.example.pliin.apppliin.data.network.dto.registerdelivery


import com.google.gson.annotations.SerializedName

data class FieldData(
    @SerializedName("numero_guia")
    val idGuia: String?,
    @SerializedName("parentesco")
    val parentesco: String?,
    @SerializedName("nombre_recibe")
    val recibio: String?,
    @SerializedName("pago")
    val pago: String?,
    @SerializedName("tipoPago")
    val tipoPago: String?,
    @SerializedName("manifiesto")
    val manifiesto: String?

)