package com.example.pliin.apppliin.domain.usecase.guides

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class GetGuideCodUseCase @Inject constructor(private val guideRepository: GuideRepository){

    suspend operator fun invoke(guia:String):Boolean{
        val response = guideRepository.getGuideCod(guia)
        return  response.messages?.get(0)?.code.equals("0")
    }
}