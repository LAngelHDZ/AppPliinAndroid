package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class ValidateExistsAddressUseCase @Inject constructor(
    private val guideRepository: GuideRepository,
    private val reloginUseCase: ReloginUseCase
) {
    suspend operator fun invoke(guia: String): Boolean {
        reloginUseCase
        val response = guideRepository.direccionValidateApi(guia)
        return response.messages?.get(0)?.code.equals("0")
    }
}