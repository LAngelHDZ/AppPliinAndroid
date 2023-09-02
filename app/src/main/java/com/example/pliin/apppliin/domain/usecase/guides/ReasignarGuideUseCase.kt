package com.example.pliin.apppliin.domain.usecase.guides

import com.example.pliin.apppliin.data.repositories.ManifestRepository
import com.example.pliin.apppliin.domain.usecase.login.ReloginUseCase
import javax.inject.Inject

class ReasignarGuideUseCase @Inject constructor(
    private val reloginUseCase: ReloginUseCase,
    private val manifestRepository: ManifestRepository
) {
    suspend operator fun invoke(idRecord:String):Boolean{
        val  response = manifestRepository.reasignarGuideManifest(idRecord)
        return response.messages!![0].code.equals("0")
    }
}