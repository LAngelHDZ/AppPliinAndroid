package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.GuideRepository
import com.example.pliin.apppliin.domain.model.deliveryItem.GetDataGuideRDItem
import javax.inject.Inject

class GetGuideCodUseCase @Inject constructor(private val guideRepository: GuideRepository){

    suspend operator fun invoke(guia:String): GetDataGuideRDItem {
        val response = guideRepository.getGuideCod(guia)
        return  response
    }
}