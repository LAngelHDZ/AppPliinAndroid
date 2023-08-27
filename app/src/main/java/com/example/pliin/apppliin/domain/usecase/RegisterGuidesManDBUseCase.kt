package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.GuideRepository
import com.example.pliin.apppliin.domain.model.GuideItem
import com.example.pliin.apppliin.domain.model.deliveryItem.DataItem
import com.example.pliin.apppliin.domain.model.toDomain
import javax.inject.Inject

class RegisterGuidesManDBUseCase @Inject constructor(
   private val guideRepository: GuideRepository
)
{
    suspend operator fun invoke(response: List<DataItem?>?) {
        if (response != null) {
            for (response in response){
                guideRepository.RegisterGuidesDB(response?.fieldData!!.toDomain())
            }
        }
    }
}