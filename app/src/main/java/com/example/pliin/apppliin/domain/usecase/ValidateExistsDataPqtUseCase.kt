package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class ValidateExistsDataPqtUseCase @Inject constructor(
    private val reloginUseCase: ReloginUseCase,
    private val guideRepository: GuideRepository
) {

    suspend operator fun invoke(guia: String): Boolean {
        reloginUseCase
        val response = guideRepository.datosPqtValidateApi(guia)

        return response.messages?.get(0)?.code.equals("0")
    }
}