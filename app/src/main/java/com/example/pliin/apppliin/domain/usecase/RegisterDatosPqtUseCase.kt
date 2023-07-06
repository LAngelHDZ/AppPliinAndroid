package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.network.dto.datospqt.DatosPqtDto
import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class RegisterDatosPqtUseCase @Inject constructor(
    private val reloginUseCase: ReloginUseCase,
    private val guideRepository: GuideRepository
) {

    suspend operator fun invoke(datosPqtDto: DatosPqtDto) {
        reloginUseCase
        do {
            val response = guideRepository.createDataPqt(datosPqtDto)
            val messageGuideValidate = response.messages!![0].code
        } while (messageGuideValidate.equals("500"))
    }
}