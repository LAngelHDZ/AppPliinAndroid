package com.example.pliin.apppliin.data.network.dto


import javax.inject.Inject

class UserDTO @Inject constructor() {

    var userValue: String = ""
    var passwordValue: String = ""


    fun getdatauser(user: String, password: String) {
        userValue = user
        passwordValue = password
    }
}
