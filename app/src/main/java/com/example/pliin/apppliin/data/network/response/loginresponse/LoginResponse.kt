package com.example.pliin.apppliin.data.network.response.loginresponse

import com.example.pliin.apppliin.data.model.TokenModel
import com.google.gson.annotations.SerializedName

data class LoginRensponse(
    @SerializedName("response") val response: TokenModel?
) {
}