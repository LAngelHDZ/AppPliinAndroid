package com.example.pliin.apppliin.data.network.response.dataguideresponse

import com.example.pliin.apppliin.data.model.GuideModel

import com.squareup.moshi.Json

data class DataGuideResponse(
    // @SerializedName("password") val password: String,
    //  @SerializedName("user") val user: String,
    @field:Json(name = "Guias") val Guias: List<GuideModel>
)
{
}