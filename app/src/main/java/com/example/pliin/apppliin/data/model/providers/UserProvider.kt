package com.example.pliin.apppliin.data.model.providers

import com.example.pliin.apppliin.data.model.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProvider @Inject constructor() {
    var users:List<UserModel> = emptyList()
}