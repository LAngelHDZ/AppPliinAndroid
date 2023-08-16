package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class InsertGuideUseCase @Inject constructor(private val repository: GuideRepository) {

    //Metodo crea un nuevo registro insertando el numero de guia cuando se valida de que no existe en el sistema
    suspend operator fun invoke(guide: kotlin.String, pago: kotlin.String?): String {
        var code: String
        do {
            val response = repository.InsertGuidetoApi(guide,pago)
            val message = response.messages!![0]!!.code
            code = message!!
        } while (!message.equals("0"))
        return code
    }
}