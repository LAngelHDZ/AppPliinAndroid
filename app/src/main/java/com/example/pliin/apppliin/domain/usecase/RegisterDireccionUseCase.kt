package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class RegisterDireccionUseCase @Inject constructor(
    private val reloginUseCase: ReloginUseCase,
    private val guideRepository: GuideRepository

) {

    suspend operator fun invoke(direccion: List<String>) {

        reloginUseCase

        do {
            val response = guideRepository.createDireccion(direccion)
            val messageGuideValidate = response.messages!![0].code
        } while (messageGuideValidate.equals("500"))

    }
}