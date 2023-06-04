package com.example.pliin.apppliin.data.network.response.queryguide


import com.example.pliin.apppliin.data.model.deliverymodel.ResponseModel
import com.google.gson.annotations.SerializedName


data class QueryGuideResponse(
    @SerializedName("response") val response: ResponseModel
) {
}