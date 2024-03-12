package com.example.pliin.apppliin.data.model.avisopago


import com.example.pliin.apppliin.data.model.datainfo.DataInfoModel
import com.google.gson.annotations.SerializedName

data class ResponseAvisoModel(
    @SerializedName("data")
    val `data`: List<DataAvisoModel?>?,
    @SerializedName("dataInfo")
    val dataInfo: DataInfoModel?
)