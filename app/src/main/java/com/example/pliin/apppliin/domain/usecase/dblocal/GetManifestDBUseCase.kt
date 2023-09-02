package com.example.pliin.apppliin.domain.usecase.dblocal

import com.example.pliin.apppliin.data.repositories.ManifestRepository
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.FieldData
import javax.inject.Inject

class GetManifestDBUseCase @Inject constructor(
private val manifestRepository: ManifestRepository

){
    suspend operator fun invoke():FieldData?{
        val response = manifestRepository.getDBManifest()
        return response
    }
}