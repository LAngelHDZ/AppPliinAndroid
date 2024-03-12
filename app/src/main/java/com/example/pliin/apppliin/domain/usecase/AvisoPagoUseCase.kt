package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.LoginRepository
import com.example.pliin.apppliin.domain.model.avisopagoitem.FieldDataAvisoItem
import javax.inject.Inject

class AvisoPagoUseCase @Inject constructor(private val loginRepository: LoginRepository, private val reloginUseCase: ReloginUseCase, private val logoutUseCase: LogoutUseCase) {

    suspend operator fun invoke(): FieldDataAvisoItem? {
        reloginUseCase.invoke()
        val reponse =loginRepository.avisoPago()?.response?.data?.get(0)?.fieldData
        logoutUseCase.invoke()
        return reponse
    }
}