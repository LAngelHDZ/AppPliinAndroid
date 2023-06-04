package com.example.pliin.apppliin.data.model.employeemodel


import com.google.gson.annotations.SerializedName

data class ResponseEM(
    @SerializedName("data")
    val `data`: List<DataEM?>?,
    @SerializedName("dataInfo")
    val dataInfo: DataInfoEM?
)