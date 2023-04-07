package com.example.pliin.apppliin.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("user") val user:String,
    @SerializedName("password") val password:String,
    @SerializedName("idEmployee") val idEmployee: String?,
    @SerializedName("employeeName") val employeeName: String?,
    @SerializedName("employeeType") val employeeType: String?,
    @SerializedName("employeeArea") val employeeArea:String?,
    @SerializedName("employeeActive") val employeeActive:Boolean?
    )








