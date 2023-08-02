package com.example.pliin.apppliin.domain.model
import com.example.pliin.apppliin.data.database.entities.SessionEntity


data class SessionItem(
    val username: String?="",
    val password: String?="",
    val token: String? = ""
)

//Funcion de mapeo de datos entre el modelo item session de la capa de dominio y la entidad session de la database
fun SessionEntity.toDomain() =
    SessionItem(
        username,
        password,
        token
    )
