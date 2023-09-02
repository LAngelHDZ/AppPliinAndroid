package com.example.pliin.apppliin.domain.usecase.manifest


import com.example.pliin.apppliin.data.repositories.ManifestRepository
import com.example.pliin.apppliin.domain.model.deliveryItem.DataItem
import com.example.pliin.apppliin.domain.usecase.login.ReloginUseCase
import javax.inject.Inject

class GetGuidesManifestUseCase @Inject constructor(
    private val reloginUseCase: ReloginUseCase,
    private val manifestRepository: ManifestRepository
){
    suspend operator fun invoke(claveManifest:String):List<DataItem?>?{
        val respose = manifestRepository.getGuidesManifest(claveManifest)
        return respose.response!!.data
    }
}