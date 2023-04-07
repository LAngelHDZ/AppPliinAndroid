package com.example.pliin.apppliin.data.network.response


import com.example.pliin.apppliin.data.model.UserModel
import com.google.gson.annotations.SerializedName

class DataUserResponse(
    @SerializedName("Users") val Users:List<UserModel>


) {
}