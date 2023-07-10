package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class RegisterGuideUseCase @Inject constructor(
    private val repository: GuideRepository,
    private val createStatusUSeCase: CreateStatusUSeCase,
    private val insertGuideUseCase: InsertGuideUseCase,
    private val updateStatusUseCase: UpdateStatusUseCase,
    private val reloginUseCase: ReloginUseCase
) {
    private var recordId = ""
    suspend operator fun invoke(
        guide: String
    ): Boolean {

        reloginUseCase()
        val code = validateGuide(guide)
        val messageInsertGuide = if (code.component1() == "401") {
            insertGuideUseCase(guide)
        } else {
            updateStatusUseCase("EN ALMACEN", code.component2())
        }
        val messageCreateStatus = createStatusUSeCase(guide, "EN ALMACEN")

        return ((messageCreateStatus == "0") and (messageInsertGuide == "0"))
    }

    //Valida que la guia exista en el sistema para tomar una decision de ingresaerla o solo actualizar su status de seguimiento
    suspend fun validateGuide(guide: String): List<String> {
        var code: String
        var recordId = ""
        do {
            val response = repository.validateGuideApi(guide)
            val messageGuideValidate = response.messages!![0]!!.code
            if (messageGuideValidate.equals("0")) {
                recordId = response.response?.data!![0]?.recordId!!
            }
            code = messageGuideValidate!!
        } while (messageGuideValidate.equals("500"))
        return listOf(code, recordId)
    }
}