package com.example.pliin.apppliin.domain.usecase.manifest

import com.example.pliin.apppliin.data.repositories.ManifestRepository
import javax.inject.Inject

class CreateManifestUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository
) {

    suspend operator fun invoke(data: List<String?>) {

        val response = manifestRepository.createManifest(data as List<String>)
    }
}