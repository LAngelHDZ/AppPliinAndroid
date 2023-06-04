package com.example.pliin.apppliin.data.model.deliverymodel


import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("data")
    val data: List<DataModel?>?,
    @SerializedName("dataInfo")
    val dataInfo: DataInfoModel?
)