package com.example.pliin.apppliin.domain.usecase.guides

import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import javax.inject.Inject

class UpdateStatusUseCase @Inject constructor(private val deliveryR: DeliveryRepository) {

    //Metodo ACtualiza el status de la guia existente en el sistema
    suspend operator fun invoke(Status: String, recordId: String,presentacion:String): String {
        var code: String
        do {
            val response = deliveryR.setUpdateStatus(Status, recordId,presentacion)
            val message = response.messages!![0]!!.code
            code = message!!
        } while (!message.equals("0"))
        return code
    }
}