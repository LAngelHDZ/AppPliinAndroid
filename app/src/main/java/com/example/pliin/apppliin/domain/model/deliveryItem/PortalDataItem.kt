package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.PortalDataModel
import com.google.gson.annotations.SerializedName

data class PortalDataItem(
    //SerializedName("direcciones")
    // val direcciones: List<DireccioneItem?>?,
    //@SerializedName("PreManifiestos")
    // val preManifiestos: List<PreManifiestoItem?>?,
    @SerializedName("manifiestoPaquetes")
    val manifiestoPaquetes: List<ManifiestoPaqueteItem?>?

)

fun PortalDataModel.toDomain() =
    PortalDataItem(
        // direcciones?.map { it!!.toDomain() },
        // preManifiestos?.map { it!!.toDomain() },
        manifiestoPaquetes?.map { it!!.toDomain() }

    )