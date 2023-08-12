package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class AddGuideManifestUseCase @Inject constructor(
    private val repository: GuideRepository,
    private val createStatusUSeCase: CreateStatusUSeCase,
    private val updateStatusUseCase: UpdateStatusUseCase,
    private val reloginUseCase: ReloginUseCase,
    private val registerGuideUseCase: RegisterGuideUseCase
) {
    suspend operator fun invoke(data: List<String>) {
        val status = "EN PROCESO DE ENTREGA"
        reloginUseCase
        val response = setGuide(data,status)
        val responseGetRecordId = registerGuideUseCase.validateGuide(data.component2())
        val recordId: List<String> = responseGetRecordId
        val updateStatus = updateStatusUseCase(status, recordId.component2(),"manifiestoPaquetes")
        val statusCreate = createStatusUSeCase(data.component2(), status)
    }

    suspend fun setGuide(data: List<String>, status: String) {
        do {
            val response = repository.setGuideManifest(data,status)
            val messageGuideValidate = response.messages!![0]!!.code
        } while (messageGuideValidate.equals("500"))
    }
}