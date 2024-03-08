package com.example.pliin.apppliin.domain.model

import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.model.GuideModel


data class GuideItem(
    val idGuia:String?,
    val color:Long?,
    val validate:Boolean?
)

//fun GuideModel.toDomain() = GuideItem(idGuia)
//fun GuideEntity.toDomain() = GuideItem(idGuia)