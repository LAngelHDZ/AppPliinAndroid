package com.example.pliin.apppliin.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginRensponse(
    // @SerializedName("password") val password: String,
    //  @SerializedName("user") val user: String,
    @SerializedName("success") val success: Boolean
) {
}