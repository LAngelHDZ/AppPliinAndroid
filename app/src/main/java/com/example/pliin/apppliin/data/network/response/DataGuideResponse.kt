package com.example.pliin.apppliin.data.network.response

import com.example.pliin.apppliin.data.model.GuideModel
import com.google.gson.annotations.SerializedName

data class DataGuideResponse(
    // @SerializedName("password") val password: String,
    //  @SerializedName("user") val user: String,
    @SerializedName("Guias") val Guias:List<GuideModel>
)
{
}