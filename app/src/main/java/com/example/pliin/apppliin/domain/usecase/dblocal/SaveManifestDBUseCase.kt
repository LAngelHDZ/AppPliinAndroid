package com.example.pliin.apppliin.domain.usecase.dblocal

import com.example.pliin.apppliin.data.repositories.ManifestRepository
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.FieldData
import javax.inject.Inject

class SaveManifestDBUseCase @Inject constructor(
private val manifestRepository: ManifestRepository
){
    suspend operator fun invoke(dataManifest: FieldData){
        manifestRepository.saveManifestDB(dataManifest)
    }
}