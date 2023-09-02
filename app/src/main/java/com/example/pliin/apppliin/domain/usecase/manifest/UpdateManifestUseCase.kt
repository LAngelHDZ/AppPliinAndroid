package com.example.pliin.apppliin.domain.usecase.manifest

import com.example.pliin.apppliin.data.repositories.ManifestRepository
import javax.inject.Inject

class UpdateManifestUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository
) {
    suspend operator fun invoke(data: List<String?>):Boolean {
        val response = manifestRepository.updateManifest(data as List<String>)
        return  response.messages?.get(0)?.code.equals("0")
    }
}