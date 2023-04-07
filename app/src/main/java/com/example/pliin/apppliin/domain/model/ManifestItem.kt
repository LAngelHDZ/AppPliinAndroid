package com.example.pliin.apppliin.domain.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ManifestItem(
    @SerializedName("manifestKey") val manifestKey:String,
    @SerializedName("fkemployye") val fkEmpleye: String,
    @SerializedName("company") val company:String?,
    @SerializedName("createDate") val createDate:Date?,
    @SerializedName("route") val route:String?,
    @SerializedName("routeStatus") val routeStatus:String?,
    @SerializedName("manifestStatus") val manifestStatus:String?,
    @SerializedName("totalPackages") val totalPackages:Int?,
    @SerializedName("totalGuides") val totalGuides:Int?
)
