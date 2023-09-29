package com.example.pliin.apppliin.domain.usecase.dblocal

import com.example.pliin.apppliin.data.repositories.GuideRepository
import com.example.pliin.apppliin.domain.model.deliveryItem.DataItem
import com.example.pliin.apppliin.domain.model.toDomain
import javax.inject.Inject

class SaveGuidesManDBUseCase @Inject constructor(
   private val guideRepository: GuideRepository
)
{
    suspend operator fun invoke(response: List<DataItem?>?) {
        if (response != null){
            for (response in response){
                guideRepository.RegisterGuidesDB(response?.fieldData!!.toDomain())
            }
        }
    }
}