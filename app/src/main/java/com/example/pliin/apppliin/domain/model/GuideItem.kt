package com.example.pliin.apppliin.domain.model

import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.model.GuideModel


data class GuideItem(val idGuia:String?)

fun GuideModel.toDomain() = GuideItem(idGuia)
fun GuideEntity.toDomain() = GuideItem(idGuia)