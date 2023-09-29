package com.example.pliin.apppliin.domain.usecase.dblocal

import com.example.pliin.apppliin.data.repositories.ManifestRepository
import javax.inject.Inject

class UpdateStatusManifestDBUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository
) {
    suspend operator fun invoke(claveManifest:String,status:String){
        manifestRepository.updateStatusManifestDB(claveManifest,status)
    }
}