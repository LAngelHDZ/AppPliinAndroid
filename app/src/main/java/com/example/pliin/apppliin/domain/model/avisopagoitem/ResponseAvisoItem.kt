package com.example.pliin.apppliin.domain.model.avisopagoitem



import com.example.pliin.apppliin.data.model.avisopago.ResponseAvisoModel
import com.example.pliin.apppliin.domain.model.datainfoitem.DataInfoItem
import com.example.pliin.apppliin.domain.model.datainfoitem.toDomain
import com.google.gson.annotations.SerializedName

data class ResponseAvisoItem(
    @SerializedName("data")
    val `data`: List<DataAvisoItem?>?,
    @SerializedName("dataInfo")
    val dataInfo: DataInfoItem?
)

fun ResponseAvisoModel.toDomain() =  ResponseAvisoItem(
    data?.map { it?.toDomain() },
    dataInfo?.toDomain()
)