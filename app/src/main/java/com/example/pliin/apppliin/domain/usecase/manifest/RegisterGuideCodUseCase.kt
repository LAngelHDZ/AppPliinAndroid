package com.example.pliin.apppliin.domain.usecase.manifest

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class RegisterGuideCodUseCase @Inject constructor(
    private val guideRepository: GuideRepository
){
    suspend operator fun invoke(guide: String, valueCod: Float, pago: String?){
        val response = guideRepository.registerGuideCod(guide,valueCod,pago)
    }
}