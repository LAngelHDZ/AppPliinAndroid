package com.example.pliin.apppliin.domain.model

import com.example.pliin.apppliin.data.database.entities.TokenEntity
import com.example.pliin.apppliin.data.model.TokenModel


data class TokenItem(
    val token: String? = "",
)

//Funcion de mapeo de datos entre modelo User de la capa de  Data y el Modelo User de la capa de Dominio
fun TokenModel.toDomain() =
    TokenItem(
        token!!
    )

//Funcion de mapeo de datos entre el modelo user de la capa de dominio y la entidad User de la database
fun TokenEntity?.toDomain() =
    TokenItem(
        this!!.token
    )
