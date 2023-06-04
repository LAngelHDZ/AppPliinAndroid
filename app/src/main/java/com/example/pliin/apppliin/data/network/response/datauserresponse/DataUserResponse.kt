package com.example.pliin.apppliin.data.network.response.datauserresponse


import com.example.pliin.apppliin.data.model.UserModel

import com.squareup.moshi.Json

class DataUserResponse(
    @field:Json(name = "response") val Users: List<UserModel>


) {
}