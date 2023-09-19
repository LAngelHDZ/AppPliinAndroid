package com.example.pliin.apppliin.domain.usecase.dblocal

import com.example.pliin.apppliin.data.repositories.GuideRepository
import com.example.pliin.apppliin.domain.model.GuideItem
import javax.inject.Inject

class GetGuidesManifestDBUseCase  @Inject constructor(
    private val guideRepository: GuideRepository
){
    suspend operator fun invoke(folioManifest:String):List<GuideItem>{
        val response = guideRepository.getGuidesDB(folioManifest)
        return response
    }
}