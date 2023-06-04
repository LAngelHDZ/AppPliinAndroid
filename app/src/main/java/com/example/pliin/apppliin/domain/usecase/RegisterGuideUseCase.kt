package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class RegisterGuideUseCase @Inject constructor(
    private val repository: GuideRepository,
    private val deliveryR: DeliveryRepository
) {
    private var recordId = ""
    suspend operator fun invoke(
        guide: String
    ): Boolean {
        val code = validateGuide(guide)
        val messageInsertGuide = if (code == "401") {
            insertGuide(guide)
        } else {
            updateStatus("EN RUTA SALTER")
        }
        val messageCreateStatus = createStatus(guide, "EN RUTA SALTER")

        return (messageCreateStatus.equals("0") and (messageInsertGuide == "0"))
    }

    //MEtodo para crear un status de seguimiento de guia
    suspend fun createStatus(guide: String, status: String): String {
        var code: String
        do {
            val responseCreateStatus = deliveryR.setCreateStatus(guide, status)
            val messageCreateStatus = responseCreateStatus.messages!![0]!!.code
            code = messageCreateStatus!!
        } while (!messageCreateStatus.equals("0"))
        return code
    }

    //Metodo ACtualiza el status de la guia existente en el sistema
    suspend fun updateStatus(Status: String): String {
        var code: String
        do {
            val response = deliveryR.setUpdateStatus(Status, recordId)
            val message = response.messages!![0]!!.code
            code = message!!
        } while (!message.equals("0"))
        return code
    }

    //Metodo crea un nuevo registro insertando el numero de guia cuando se valida de que no existe en el sistema
    suspend fun insertGuide(guide: String): String {
        var code: String
        do {
            val response = repository.InsertGuidetoApi(guide)
            val message = response.messages!![0]!!.code
            code = message!!
        } while (!message.equals("0"))
        return code
    }

    //Valida que la guia exista en el sistema para tomar una decision de ingresaerla o solo actualizar su status de seguimiento
    suspend fun validateGuide(guide: String): String {
        var code: String
        do {
            val response = repository.validateGuideApi(guide)
            val messageGuideValidate = response.messages!![0]!!.code
            if (messageGuideValidate.equals("0")) {
                recordId = response.response?.data!![0]?.recordId!!
            }
            code = messageGuideValidate!!
        } while (messageGuideValidate.equals("500"))
        return code
    }
}