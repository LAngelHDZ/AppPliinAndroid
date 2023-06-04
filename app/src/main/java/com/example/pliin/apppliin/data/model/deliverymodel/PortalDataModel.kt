package com.example.pliin.apppliin.data.model.deliverymodel


import com.google.gson.annotations.SerializedName

data class PortalDataModel(
    // @SerializedName("direcciones")
    //  val direcciones: List<DireccioneModel?>?,
    // @SerializedName("PreManifiestos")
    // val preManifiestos: List<PreManifiestoModel?>?,
    @SerializedName("manifiestoPaquetes")
    val manifiestoPaquetes: List<ManifiestoPaqueteModel?>?

)