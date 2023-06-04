package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class QueryGuideValidateUseCase @Inject constructor(private val guideRepository: GuideRepository) {

    suspend operator fun invoke(guide: String): List<String?> {
        val response = guideRepository.GuideQueryAarrastre(guide)
        val status = response.messages?.get(0)?.code
        return if (status.equals("0")) {
            val guide = response.response?.data?.get(0)?.fieldData?.idGuia
            val recordid =
                response.response?.data?.get(0)?.portalData?.manifiestoPaquetes?.get(0)?.recordId
            listOf(guide, recordid)
        } else {
            listOf("", "")
        }
    }
}